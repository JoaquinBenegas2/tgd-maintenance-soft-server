package com.tgd.maintenance_soft_server.modules.maintenance.controllers;

import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceTypeRequestDto;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceTypeResponseDto;
import com.tgd.maintenance_soft_server.modules.maintenance.services.MaintenanceTypeService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/maintenance-types")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class MaintenanceTypeController {

    private final AuthService authService;
    private final MaintenanceTypeService maintenanceTypeService;

    @GetMapping
    public ResponseEntity<List<MaintenanceTypeResponseDto>> getMaintenanceTypeList(@RequestHeader("x-plant-id") Long plantId) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(maintenanceTypeService.getAll(plantEntity));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<MaintenanceTypeResponseDto> getMaintenanceTypeById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(maintenanceTypeService.getById(id, plantEntity));
    }

    @PostMapping
    public ResponseEntity<MaintenanceTypeResponseDto> createMaintenanceType(@RequestHeader("x-plant-id") Long plantId, @RequestBody MaintenanceTypeRequestDto maintenanceTypeRequestDto) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(maintenanceTypeService.create(plantEntity, maintenanceTypeRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MaintenanceTypeResponseDto> updateMaintenanceType(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id, @RequestBody MaintenanceTypeRequestDto maintenanceTypeRequestDto) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(maintenanceTypeService.update(id, plantEntity, maintenanceTypeRequestDto));
    }

    @DeleteMapping("/{id}")
    public void deleteMaintenanceTypeById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        maintenanceTypeService.deleteById(id, plantEntity);
    }
}
