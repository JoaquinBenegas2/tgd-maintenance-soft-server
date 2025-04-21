package com.tgd.maintenance_soft_server.modules.route.services.implementation;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementResponseDto;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.element.repositories.ElementRepository;
import com.tgd.maintenance_soft_server.modules.plant.dtos.PlantResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
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

import java.util.List;
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
    public RouteResponseDto createRoute(RouteRequestDto routeRequestDto, PlantEntity plantEntity) {
        RouteEntity route = new RouteEntity();
        route.setName(routeRequestDto.getName());
        route.setDescription(routeRequestDto.getDescription());
        route.setPeriodicityInDays(routeRequestDto.getPeriodicityInDays());
        route.setStartDate(routeRequestDto.getStartDate());
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
    public RouteResponseDto updateRoute(Long id, RouteUpdateRequestDto dto, PlantEntity plantEntity) {
        RouteEntity route = routeRepository.findByIdAndIdentifyingEntity(id, plantEntity)
                .orElseThrow(() -> new RuntimeException("Route not found or not associated with plant"));

        route.setName(dto.getName());
        route.setDescription(dto.getDescription());
        route.setPeriodicityInDays(dto.getPeriodicityInDays());
        route.setStartDate(dto.getStartDate());

        RouteEntity routeEntityUpdated = routeRepository.save(route);
        return mapToResponseDto(routeEntityUpdated);
    }

    @Override
    public RouteResponseDto assignUserToRoute(Long routeId, Long userId) {
        RouteEntity route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Route not found"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        route.assignUser(user);

        return mapToResponseDto(routeRepository.save(route));
    }

    @Override
    public RouteResponseDto unassignUserFromRoute(Long routeId, Long userId) {
        RouteEntity route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Route not found"));

        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        route.unassignUser(user);

        return mapToResponseDto(routeRepository.save(route));
    }

    @Override
    public RouteResponseDto assignElementToRoute(Long routeId, Long elementId) {
        RouteEntity route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Plant not found"));

        ElementEntity element = elementRepository.findById(elementId)
                .orElseThrow(() -> new EntityNotFoundException("Element not found"));

        route.assignElement(element);

        return mapToResponseDto(routeRepository.save(route));
    }

    @Override
    public RouteResponseDto unassignElementFromRoute(Long routeId, Long elementId) {
        RouteEntity route = routeRepository.findById(routeId)
                .orElseThrow(() -> new EntityNotFoundException("Plant not found"));

        ElementEntity element = elementRepository.findById(elementId)
                .orElseThrow(() -> new EntityNotFoundException("Element not found"));

        route.unassignElement(element);

        return mapToResponseDto(routeRepository.save(route));
    }


    public RouteResponseDto mapToResponseDto(RouteEntity entity) {
        RouteResponseDto routeResponseDto = new RouteResponseDto();
        routeResponseDto.setId(entity.getId());
        routeResponseDto.setName(entity.getName());
        routeResponseDto.setDescription(entity.getDescription());
        routeResponseDto.setStartDate(entity.getStartDate());
        routeResponseDto.setPeriodicityInDays(entity.getPeriodicityInDays());
        routeResponseDto.setStatus(entity.getStatus());

        routeResponseDto.setAssignedElements(entity.getAssignedElements().stream()
                .map(e -> modelMapper.map(e, ElementResponseDto.class))
                .toList());

        routeResponseDto.setAssignedOperators(entity.getAssignedOperators().stream()
                .map(u -> modelMapper.map(u, UserResponseDto.class))
                .toList());

        return routeResponseDto;
    }

}
