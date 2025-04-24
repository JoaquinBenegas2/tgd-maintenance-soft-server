package com.tgd.maintenance_soft_server.modules.route.services;

import com.tgd.maintenance_soft_server.lib.blo_service.interfaces.BloServiceInterface;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.route.dtos.ProgressRouteResponseDto;
import com.tgd.maintenance_soft_server.modules.route.dtos.RouteRequestDto;
import com.tgd.maintenance_soft_server.modules.route.dtos.RouteResponseDto;
import com.tgd.maintenance_soft_server.modules.route.dtos.RouteUpdateRequestDto;
import com.tgd.maintenance_soft_server.modules.route.entities.RouteEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface RouteService extends BloServiceInterface<RouteRequestDto, RouteResponseDto, RouteEntity, Long, PlantEntity> {
    List<RouteResponseDto> getAllRoutes(PlantEntity plantEntity);

    RouteResponseDto getRouteById(Long id, PlantEntity plantEntity);

    RouteResponseDto createRoute(RouteRequestDto routeRequestDto, PlantEntity plantEntity);

    RouteResponseDto updateRoute(Long id, RouteUpdateRequestDto routeRequestDto, PlantEntity plantEntity);

    RouteResponseDto assignUserToRoute(Long routeId, Long userId);

    RouteResponseDto unassignUserFromRoute(Long routeId, Long userId);

    RouteResponseDto assignElementToRoute(Long routeId, Long elementId);

    RouteResponseDto unassignElementFromRoute(Long routeId, Long elementId);

    List<ProgressRouteResponseDto> getTodayRoutes(PlantEntity plantEntity);

    Map<Integer, List<ProgressRouteResponseDto>> getRoutesOfTheWeek(PlantEntity plantEntity);

    List<ProgressRouteResponseDto> getDelayedRoutes(PlantEntity plantEntity);

    RouteResponseDto mapToResponseDto(RouteEntity entity);
}
