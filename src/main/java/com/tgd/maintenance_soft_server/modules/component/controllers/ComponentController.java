package com.tgd.maintenance_soft_server.modules.component.controllers;

import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentWithoutAssetResponseDto;
import com.tgd.maintenance_soft_server.modules.component.models.ComponentStatus;
import com.tgd.maintenance_soft_server.modules.component.services.ComponentService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/components")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ComponentController {

    private final AuthService authService;
    private final ComponentService componentService;

    @GetMapping
    public ResponseEntity<List<ComponentResponseDto>> getComponentList(
            @RequestHeader("x-plant-id") Long plantId,
            @RequestParam(value = "status", required = false) ComponentStatus status
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);

        if (status != null) {
            return ResponseEntity.ok(componentService.getAllByStatus(plantEntity, status));
        }

        return ResponseEntity.ok(componentService.getAll(plantEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ComponentResponseDto> getComponentById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(componentService.getById(id, plantEntity));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ComponentResponseDto> updateComponentStatus(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long id,
            @RequestParam ComponentStatus status) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(componentService.updateComponentStatus(id, status, plantEntity));
    }

    @DeleteMapping("/{id}")
    public void deleteComponentById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        componentService.deleteComponentById(id, plantEntity);
    }
}
