package com.tgd.maintenance_soft_server.modules.route.schedulers;

import com.tgd.maintenance_soft_server.modules.email.dtos.EmailRequestDto;
import com.tgd.maintenance_soft_server.modules.email.models.EmailType;
import com.tgd.maintenance_soft_server.modules.email.services.EmailService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.plant.repositories.PlantRepository;
import com.tgd.maintenance_soft_server.modules.route.dtos.DelayedRouteItemDto;
import com.tgd.maintenance_soft_server.modules.route.dtos.ProgressRouteResponseDto;
import com.tgd.maintenance_soft_server.modules.route.entities.RouteEntity;
import com.tgd.maintenance_soft_server.modules.route.entities.RouteNotificationStatusEntity;
import com.tgd.maintenance_soft_server.modules.route.repositories.RouteNotificationStatusRepository;
import com.tgd.maintenance_soft_server.modules.route.repositories.RouteRepository;
import com.tgd.maintenance_soft_server.modules.route.services.RouteService;
import com.tgd.maintenance_soft_server.modules.user.dtos.UserResponseDto;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class DelayedRouteNotifierScheduler {

    private final RouteService routeService;
    private final PlantRepository plantRepository;
    private final EmailService emailService;
    private final RouteNotificationStatusRepository notificationStatusRepository;
    private final RouteRepository routeRepository;

    @Value("${client.base-url}")
    private String clientBaseUrl;

    @Transactional
    @Scheduled(cron = "0 */2 * * * ?") // Cada 2 minutos
    public void notifyDelayedRoutes() {
        LocalDate today = LocalDate.now();

        List<PlantEntity> plants = plantRepository.findAll();
        Set<Long> todayDelayedRouteIds = new HashSet<>();

        for (PlantEntity plant : plants) {
            List<ProgressRouteResponseDto> delayedRoutes = routeService.getDelayedRoutes(plant);

            List<Long> plantRouteIds = delayedRoutes.stream()
                    .map(ProgressRouteResponseDto::getId)
                    .toList();

            todayDelayedRouteIds.addAll(plantRouteIds);

            for (ProgressRouteResponseDto progressRouteResponseDto : delayedRoutes) {
                Long routeId = progressRouteResponseDto.getId();

                RouteEntity routeEntity = routeRepository.findById(routeId)
                        .orElseThrow(() -> new IllegalStateException("Ruta no encontrada con ID: " + routeId));

                boolean alreadyNotified = notificationStatusRepository.findByRoute(routeEntity).isPresent();

                if (!alreadyNotified) {
                    sendDelayedRoutesEmail(progressRouteResponseDto, plant);
                    notificationStatusRepository.save(new RouteNotificationStatusEntity(routeEntity, today));
                }
            }
        }

        List<RouteNotificationStatusEntity> allNotifications = notificationStatusRepository.findAll();

        for (RouteNotificationStatusEntity routeNotificationStatus : allNotifications) {
            Long routeId = routeNotificationStatus.getRoute().getId();

            if (!todayDelayedRouteIds.contains(routeId)) {
                notificationStatusRepository.delete(routeNotificationStatus);
            }
        }
    }

    private void sendDelayedRoutesEmail(ProgressRouteResponseDto routeDto, PlantEntity plant) {
        String subject = "🔔 Overdue maintenance route: " + routeDto.getName();

        LocalDate lastExpectedDate = calculateLastExpectedDate(routeDto);

        String plantName = plant.getName();

        List<String> plantSupervisorEmails = plant.getAssignedUsers()
                .stream()
                .filter(user -> "PLANT_SUPERVISOR".equals(user.getRole()))
                .map(UserEntity::getEmail)
                .distinct()
                .toList();

        List<String> plantOperatorEmails = routeDto.getAssignedOperators()
                .stream()
                .map(UserResponseDto::getEmail)
                .distinct()
                .toList();

        List<String> allRecipients = Stream.of(
                        plantSupervisorEmails.stream(),
                        plantOperatorEmails.stream()
                )
                .flatMap(s -> s)
                .distinct()
                .toList();

        List<DelayedRouteItemDto> delayedRoutesForTemplate = List.of(
                new DelayedRouteItemDto(routeDto.getName(), lastExpectedDate.toString())
        );

        String plantSlug = plant.getName().toLowerCase().replaceAll("\\s", "-");

        EmailRequestDto emailRequestDto = new EmailRequestDto();
        emailRequestDto.setTo(allRecipients);
        emailRequestDto.setSubject(subject);
        emailRequestDto.setEmailType(EmailType.DELAYED_ROUTES);
        emailRequestDto.setVariables(Map.of(
                "supervisorName", "Supervisor",
                "plantName", plantName,
                "delayedRoutes", delayedRoutesForTemplate,
                "dashboardUrl", clientBaseUrl + "/" + plantSlug + "/home"
        ));

        emailService.sendTemplatedEmail(emailRequestDto);
    }

    private LocalDate calculateLastExpectedDate(ProgressRouteResponseDto progressRouteResponseDto) {
        LocalDate today = LocalDate.now();

        LocalDate startDate = progressRouteResponseDto.getStartDate();
        int periodicity = progressRouteResponseDto.getPeriodicityInDays();

        long diffDays = ChronoUnit.DAYS.between(startDate, today);
        if (diffDays < 0) {
            return startDate;
        }
        long completedIntervals = diffDays / periodicity;
        return startDate.plusDays(completedIntervals * periodicity);
    }
}
