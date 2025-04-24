package com.tgd.maintenance_soft_server.modules.route.controllers;

import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.plant.dtos.PlantResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.route.dtos.ProgressRouteResponseDto;
import com.tgd.maintenance_soft_server.modules.route.dtos.RouteRequestDto;
import com.tgd.maintenance_soft_server.modules.route.dtos.RouteResponseDto;
import com.tgd.maintenance_soft_server.modules.route.dtos.RouteUpdateRequestDto;
import com.tgd.maintenance_soft_server.modules.route.services.RouteService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/routes")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class RouteController {

    private final AuthService authService;
    private final RouteService routeService;

    @GetMapping
    public ResponseEntity<List<RouteResponseDto>> getRouteList(@RequestHeader("x-plant-id") Long plantId) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(routeService.getAllRoutes(plantEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<RouteResponseDto> getRouteById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(routeService.getRouteById(id, plantEntity));
    }

    @GetMapping("/today")
    public ResponseEntity<List<ProgressRouteResponseDto>> getTodayRoutes(@RequestHeader("x-plant-id") Long plantId) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(routeService.getTodayRoutes(plantEntity));
    }

    @GetMapping("/week")
    public ResponseEntity<Map<Integer, List<ProgressRouteResponseDto>>> getRoutesOfTheWeek(@RequestHeader("x-plant-id") Long plantId) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(routeService.getRoutesOfTheWeek(plantEntity));
    }

    @GetMapping("/delayed")
    public ResponseEntity<List<ProgressRouteResponseDto>> getDelayedRoutes(@RequestHeader("x-plant-id") Long plantId) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(routeService.getDelayedRoutes(plantEntity));
    }

    @PostMapping
    public ResponseEntity<RouteResponseDto> createRoute(
            @RequestHeader("x-plant-id") Long plantId,
            @RequestBody RouteRequestDto dto
    ) {
        PlantEntity plant = authService.getSelectedPlant(plantId);
        RouteResponseDto created = routeService.createRoute(dto, plant);
        return ResponseEntity.ok(created);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RouteResponseDto> updateRoute(
            @PathVariable Long id,
            @RequestHeader("x-plant-id") Long plantId,
            @RequestBody RouteUpdateRequestDto dto
    ) {
        PlantEntity plant = authService.getSelectedPlant(plantId);
        RouteResponseDto updated = routeService.updateRoute(id, dto, plant);
        return ResponseEntity.ok(updated);
    }

    @PostMapping("/{routeId}/users/{userId}")
    @Operation(description = "Assigns a user to a route")
    public ResponseEntity<RouteResponseDto> assignUserToRoute(
            @PathVariable Long routeId,
            @PathVariable Long userId
    ) {
        RouteResponseDto updatedRoute = routeService.assignUserToRoute(routeId, userId);
        return ResponseEntity.ok(updatedRoute);
    }

    @DeleteMapping("/{routeId}/users/{userId}")
    @Operation(description = "Unassigns a user from a route")
    public ResponseEntity<RouteResponseDto> unassignUserFromRoute(
            @PathVariable Long routeId,
            @PathVariable Long userId
    ) {
        RouteResponseDto updatedRoute = routeService.unassignUserFromRoute(routeId, userId);
        return ResponseEntity.ok(updatedRoute);
    }

    @PostMapping("/{routeId}/elements/{elementId}")
    @Operation(description = "Assigns a element to a route")
    public ResponseEntity<RouteResponseDto> assignElementToRoute(
            @PathVariable Long routeId,
            @PathVariable Long elementId
    ) {
        RouteResponseDto updatedRoute = routeService.assignElementToRoute(routeId, elementId);
        return ResponseEntity.ok(updatedRoute);
    }

    @DeleteMapping("/{routeId}/elements/{elementId}")
    @Operation(description = "Unassigns a user from a route")
    public ResponseEntity<RouteResponseDto> unassignElementFromRoute(
            @PathVariable Long routeId,
            @PathVariable Long elementId
    ) {
        RouteResponseDto updatedRoute = routeService.unassignElementFromRoute(routeId, elementId);
        return ResponseEntity.ok(updatedRoute);
    }

    @DeleteMapping("/{id}")
    public void deleteRouteById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        routeService.deleteById(id, plantEntity);
    }
}
