package com.tgd.maintenance_soft_server.modules.element.controllers;

import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementResponseDto;
import com.tgd.maintenance_soft_server.modules.element.models.ElementStatus;
import com.tgd.maintenance_soft_server.modules.element.services.ElementService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/elements")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ElementController {

    private final AuthService authService;
    private final ElementService elementService;

    @GetMapping
    public ResponseEntity<List<ElementResponseDto>> getElementList(
            @RequestHeader("x-plant-id") Long plantId,
            @RequestParam(value = "status", required = false) ElementStatus status
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);

        if (status != null) {
            return ResponseEntity.ok(elementService.getAllByStatus(plantEntity, status));
        }

        return ResponseEntity.ok(elementService.getAll(plantEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ElementResponseDto> getElementById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(elementService.getById(id, plantEntity));
    }

    @DeleteMapping("/{id}")
    public void deleteElementById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        elementService.deleteById(id, plantEntity);
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<ElementResponseDto> updateElementStatus(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long id,
            @RequestParam ElementStatus status) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(elementService.updateElementStatus(id, status, plantEntity));
    }

    @GetMapping("/by-status")
    public ResponseEntity<List<ElementResponseDto>> getAllByStatus(
            @RequestHeader("x-plant-id") Long plantId,
            @RequestParam ElementStatus status) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(elementService.getAllByStatus(plantEntity, status));
    }
}
