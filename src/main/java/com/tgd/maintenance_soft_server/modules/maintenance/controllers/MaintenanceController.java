package com.tgd.maintenance_soft_server.modules.maintenance.controllers;

import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceRequestDto;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceResponseDto;
import com.tgd.maintenance_soft_server.modules.maintenance.services.MaintenanceService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenances")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MaintenanceController {

    private final AuthService authService;
    private final MaintenanceService maintenanceService;

    @GetMapping
    public ResponseEntity<List<MaintenanceResponseDto>> getMaintenanceList(@RequestHeader("x-plant-id") Long plantId) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(maintenanceService.getAll(plantEntity));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceResponseDto> getMaintenanceById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(maintenanceService.getById(id, plantEntity));
    }

    @PostMapping
    public ResponseEntity<MaintenanceResponseDto> createMaintenance(@RequestHeader("x-plant-id") Long plantId, @RequestBody MaintenanceRequestDto maintenanceRequestDto) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(maintenanceService.createMaintenance(plantEntity, maintenanceRequestDto));
    }

    @DeleteMapping("/{id}")
    public void deleteMaintenanceById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        maintenanceService.deleteById(id, plantEntity);
    }
}
