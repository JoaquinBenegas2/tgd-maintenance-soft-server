package com.tgd.maintenance_soft_server.modules.element.controllers;

import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.element.services.ElementService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementResponseDto;
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
    public ResponseEntity<List<ElementResponseDto>> getElementList(@RequestHeader("x-plant-id") Long plantId) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(elementService.getAll(plantEntity));
    }

    @DeleteMapping("/{id}")
    public void deleteElementById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        elementService.deleteById(id, plantEntity);
    }
}
