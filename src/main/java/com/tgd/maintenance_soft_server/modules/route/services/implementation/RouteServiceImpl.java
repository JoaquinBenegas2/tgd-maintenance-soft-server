package com.tgd.maintenance_soft_server.modules.route.services.implementation;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementResponseDto;
import com.tgd.maintenance_soft_server.modules.element.dtos.ProgressElementResponseDto;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.element.repositories.ElementRepository;
import com.tgd.maintenance_soft_server.modules.maintenance.repositories.MaintenanceRepository;
import com.tgd.maintenance_soft_server.modules.plant.dtos.PlantResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.route.dtos.ProgressRouteResponseDto;
import com.tgd.maintenance_soft_server.modules.route.dtos.RouteUpdateRequestDto;
import com.tgd.maintenance_soft_server.modules.route.models.RouteStatus;
import com.tgd.maintenance_soft_server.modules.route.repositories.RouteRepository;
import com.tgd.maintenance_soft_server.modules.route.dtos.RouteRequestDto;
import com.tgd.maintenance_soft_server.modules.route.dtos.RouteResponseDto;
import com.tgd.maintenance_soft_server.modules.route.entities.RouteEntity;
import com.tgd.maintenance_soft_server.modules.route.services.RouteService;
import com.tgd.maintenance_soft_server.modules.user.dtos.UserResponseDto;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import com.tgd.maintenance_soft_server.modules.user.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RouteServiceImpl
        extends BloService<RouteRequestDto, RouteResponseDto, RouteEntity, Long, PlantEntity>
        implements RouteService {

    private final RouteRepository routeRepository;
    private final ElementRepository elementRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final MaintenanceRepository maintenanceRepository;

    @Override
    public RouteRepository getRepository() {
        return routeRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<RouteResponseDto> getAllRoutes(PlantEntity plantEntity) {
        return routeRepository.findAllByIdentifyingEntity(plantEntity).stream()
                .map(this::mapToResponseDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public RouteResponseDto getRouteById(Long id, PlantEntity plantEntity) {
        RouteEntity route = routeRepository.findByIdAndIdentifyingEntity(id, plantEntity)
                .orElseThrow(() -> new RuntimeException("Route not found or not associated with plant"));

        return mapToResponseDto(route);
    }

    @Override
    @Transactional
    public RouteResponseDto createRoute(RouteRequestDto routeRequestDto, PlantEntity plantEntity) {
        RouteEntity route = new RouteEntity();
        route.setName(routeRequestDto.getName());
        route.setDescription(routeRequestDto.getDescription());
        route.setPeriodicityInDays(routeRequestDto.getPeriodicityInDays());
        route.setStartDate(routeRequestDto.getStartDate());
        route.setActiveFromDate(routeRequestDto.getStartDate());
        route.setStatus(RouteStatus.ACTIVE);
        route.setIdentifyingEntity(plantEntity);

        List<ElementEntity> elements = elementRepository.findAllById(
                List.of(routeRequestDto.getElementIds())
        );

        List<UserEntity> operators = userRepository.findAllById(
                List.of(routeRequestDto.getOperatorIds())
        );

        route.setAssignedElements(elements);
        route.setAssignedOperators(operators);

        RouteEntity routeEntitySaved = routeRepository.save(route);
        return mapToResponseDto(routeEntitySaved);
    }

    @Override
    @Transactional
    public RouteResponseDto updateRoute(Long id, RouteUpdateRequestDto dto, PlantEntity plantEntity) {
        RouteEntity route = routeRepository.findByIdAndIdentifyingEntity(id, plantEntity)
                .orElseThrow(() -> new RuntimeException("Route not found or not associated with plant"));

        if (!route.getStartDate().isEqual(dto.getStartDate())) {
            boolean hasMaintenances = maintenanceRepository.existsByRoute(route);

            if (hasMaintenances) {
                throw new IllegalStateException("Cannot change start date because maintenances already exist for this route");
            }

            route.setStartDate(dto.getStartDate());
        }

        route.setName(dto.getName());
        route.setDescription(dto.getDescription());
        route.setPeriodicityInDays(dto.getPeriodicityInDays());

        RouteEntity routeEntityUpdated = routeRepository.save(route);
        return mapToResponseDto(routeEntityUpdated);
    }

    @Transactional
    @Override
    public RouteResponseDto assignUserToRoute(Long routeId, Long userId) {
        RouteEntity route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Route not found"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        route.assignUser(user);

        return mapToResponseDto(routeRepository.save(route));
    }

    @Transactional
    @Override
    public RouteResponseDto unassignUserFromRoute(Long routeId, Long userId) {
        RouteEntity route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Route not found"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        route.unassignUser(user);

        return mapToResponseDto(routeRepository.save(route));
    }

    @Transactional
    @Override
    public RouteResponseDto assignElementToRoute(Long routeId, Long elementId) {
        RouteEntity route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Plant not found"));

        ElementEntity element = elementRepository.findById(elementId)
                .orElseThrow(() -> new EntityNotFoundException("Element not found"));

        route.assignElement(element);

        return mapToResponseDto(routeRepository.save(route));
    }

    @Transactional
    @Override
    public RouteResponseDto unassignElementFromRoute(Long routeId, Long elementId) {
        RouteEntity route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Plant not found"));

        ElementEntity element = elementRepository.findById(elementId)
                .orElseThrow(() -> new EntityNotFoundException("Element not found"));

        route.unassignElement(element);

        return mapToResponseDto(routeRepository.save(route));
    }

    @Override
    public List<ProgressRouteResponseDto> getTodayRoutes(PlantEntity plantEntity) {
        LocalDate today = LocalDate.now();
        List<RouteEntity> routes = routeRepository.findAllByStatusIsAndIdentifyingEntity(RouteStatus.ACTIVE, plantEntity);

        return routes.stream()
                .filter(r -> daysBetween(r.getStartDate(), today) % r.getPeriodicityInDays() == 0)
                .map(r -> mapToProgressRoute(r, today, plantEntity))
                .collect(Collectors.toList());
    }

    @Override
    public Map<Integer, List<ProgressRouteResponseDto>> getRoutesOfTheWeek(PlantEntity plantEntity) {
        LocalDate today = LocalDate.now();
        List<RouteEntity> routes = routeRepository.findAllByStatusIsAndIdentifyingEntity(RouteStatus.ACTIVE, plantEntity);
        Map<Integer, List<ProgressRouteResponseDto>> result = new HashMap<>();

        for (int i = 1; i <= 7; i++) {
            LocalDate targetDate = today.plusDays(i);
            List<ProgressRouteResponseDto> routesForDay = routes.stream()
                    .filter(r -> daysBetween(r.getStartDate(), targetDate) % r.getPeriodicityInDays() == 0)
                    .map(r -> mapToProgressRoute(r, targetDate, plantEntity))
                    .collect(Collectors.toList());
            result.put(i, routesForDay);
        }

        return result;
    }

    @Override
    public List<ProgressRouteResponseDto> getDelayedRoutes(PlantEntity plantEntity) {
        LocalDate today = LocalDate.now();
        List<RouteEntity> routes = routeRepository.findAllByStatusIsAndIdentifyingEntity(RouteStatus.ACTIVE, plantEntity);

        return routes.stream()
                .filter(route -> route.getPeriodicityInDays() != 1)
                .map(route -> {
                    long diff = daysBetween(route.getStartDate(), today);
                    if (diff < 0) return null;
                    long completedIntervals = diff / route.getPeriodicityInDays();
                    LocalDate lastDate = route.getStartDate().plusDays(completedIntervals * route.getPeriodicityInDays());

                    boolean allMaintained = route.getAssignedElements().stream()
                            .noneMatch(element -> maintenanceRepository
                                    .findAllByRouteIdAndElementIdAndMaintenanceDateGreaterThanEqualAndIdentifyingEntity(
                                            route.getId(), element.getId(), lastDate, plantEntity
                                    ).isEmpty());

                    return allMaintained ? null : mapToProgressRoute(route, lastDate, plantEntity);
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }


    public RouteResponseDto mapToResponseDto(RouteEntity entity) {
        RouteResponseDto routeResponseDto = new RouteResponseDto();
        routeResponseDto.setId(entity.getId());
        routeResponseDto.setName(entity.getName());
        routeResponseDto.setDescription(entity.getDescription());
        routeResponseDto.setStartDate(entity.getStartDate());
        routeResponseDto.setPeriodicityInDays(entity.getPeriodicityInDays());
        routeResponseDto.setStatus(entity.getStatus());
        routeResponseDto.setActiveFromDate(entity.getActiveFromDate());

        routeResponseDto.setAssignedElements(entity.getAssignedElements().stream()
                .map(e -> modelMapper.map(e, ElementResponseDto.class))
                .toList());

        routeResponseDto.setAssignedOperators(entity.getAssignedOperators().stream()
                .map(u -> modelMapper.map(u, UserResponseDto.class))
                .toList());

        return routeResponseDto;
    }

    public ProgressRouteResponseDto mapToProgressRoute(RouteEntity route, LocalDate date, PlantEntity plantEntity) {
        ProgressRouteResponseDto routeResponseDto = new ProgressRouteResponseDto();
        routeResponseDto.setId(route.getId());
        routeResponseDto.setName(route.getName());
        routeResponseDto.setDescription(route.getDescription());
        routeResponseDto.setStartDate(route.getStartDate());
        routeResponseDto.setPeriodicityInDays(route.getPeriodicityInDays());
        routeResponseDto.setStatus(route.getStatus());
        routeResponseDto.setActiveFromDate(route.getActiveFromDate());

        List<ProgressElementResponseDto> elementDtoList = route.getAssignedElements().stream()
                .map(element -> {
                    ProgressElementResponseDto elementDto = modelMapper.map(element, ProgressElementResponseDto.class);
                    boolean received = !maintenanceRepository
                            .findAllByRouteIdAndElementIdAndMaintenanceDateGreaterThanEqualAndIdentifyingEntity(
                                    route.getId(), element.getId(), date, plantEntity
                            ).isEmpty();
                    elementDto.setReceivedMaintenance(received);
                    return elementDto;
                })
                .collect(Collectors.toList());

        routeResponseDto.setAssignedElements(elementDtoList);

        routeResponseDto.setAssignedOperators(route.getAssignedOperators().stream()
                .map(u -> modelMapper.map(u, UserResponseDto.class))
                .toList());

        return routeResponseDto;
    }

    private long daysBetween(LocalDate from, LocalDate to) {
        return ChronoUnit.DAYS.between(from, to);
    }

    public List<RouteResponseDto> getAllByStatus(PlantEntity plantEntity, RouteStatus status) {
        return routeRepository.findAllByIdentifyingEntityAndStatus(plantEntity, status).stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    @Transactional
    public RouteResponseDto updateRouteStatus(Long id, RouteStatus status, PlantEntity plantEntity) {
        RouteEntity route = routeRepository.findByIdAndIdentifyingEntity(id, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Route not found"));

        RouteStatus previousStatus = route.getStatus();

        if (previousStatus == RouteStatus.INACTIVE && status == RouteStatus.ACTIVE) {
            route.setActiveFromDate(LocalDate.now());
        }

        route.setStatus(status);

        return mapToResponseDto(routeRepository.save(route));
    }
}
