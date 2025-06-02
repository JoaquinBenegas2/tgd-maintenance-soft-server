package com.tgd.maintenance_soft_server.modules.maintenance.controllers;

import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceRequestDto;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceResponseDto;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceUpdateRequestDto;
import com.tgd.maintenance_soft_server.modules.maintenance.services.MaintenanceService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MaintenanceController {

    private final AuthService authService;
    private final MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<MaintenanceResponseDto>> getMaintenanceList(
            @RequestHeader("x-plant-id") Long plantId,
            @RequestParam(value = "elementId", required = false) Long elementId,
            @RequestParam(value = "componentId", required = false) Long componentId,
            @RequestParam(value = "assetId", required = false) Long assetId,
            @RequestParam(value = "dateFrom", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateFrom,
            @RequestParam(value = "dateTo", required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate dateTo
    ) {
        PlantEntity plant = authService.getSelectedPlant(plantId);

        if (dateFrom == null || dateTo == null) {
            return ResponseEntity.ok(
                    maintenanceService.getAll(plant)
            );
        }

        if (elementId != null) {
            return ResponseEntity.ok(
                    maintenanceService.getAllByElementAndDateRange(plant, elementId, dateFrom, dateTo)
            );
        }
        if (componentId != null) {
            return ResponseEntity.ok(
                    maintenanceService.getAllByComponentAndDateRange(plant, componentId, dateFrom, dateTo)
            );
        }
        if (assetId != null) {
            return ResponseEntity.ok(
                    maintenanceService.getAllByAssetAndDateRange(plant, assetId, dateFrom, dateTo)
            );
        }

        return ResponseEntity.ok(maintenanceService.getAll(plant));
    }

    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDto> getMaintenanceById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(maintenanceService.getById(id, plantEntity));
    }

    @PostMapping
    public ResponseEntity<MaintenanceResponseDto> createMaintenance(
            @AuthenticationPrincipal Jwt jwt,
            @RequestHeader("x-plant-id") Long plantId,
            @RequestBody MaintenanceRequestDto maintenanceRequestDto
    ) {
        UserEntity userEntity = authService.getAuthenticatedUser(jwt);
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(maintenanceService.createMaintenance(userEntity, plantEntity, maintenanceRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDto> updateMaintenance(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long id,
            @RequestBody MaintenanceUpdateRequestDto maintenanceRequestDto
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(maintenanceService.updateMaintenance(id, plantEntity, maintenanceRequestDto));
    }

    @DeleteMapping("/{id}")
    public void deleteMaintenanceById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        maintenanceService.deleteById(id, plantEntity);
    }
}
