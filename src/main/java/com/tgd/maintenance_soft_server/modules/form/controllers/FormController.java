package com.tgd.maintenance_soft_server.modules.form.controllers;

import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormRequestDto;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormResponseDto;
import com.tgd.maintenance_soft_server.modules.form.services.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/forms")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class FormController {

    private final AuthService authService;
    private final FormService formService;

    @GetMapping
    public ResponseEntity<List<FormResponseDto>> getFormList(@RequestHeader("x-plant-id") Long plantId) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(formService.getAll(plantEntity));
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<FormResponseDto> getFormById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(formService.getById(id, plantEntity));
    }

    @PostMapping
    public ResponseEntity<FormResponseDto> createForm(@RequestHeader("x-plant-id") Long plantId, @RequestBody FormRequestDto formRequestDto) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(formService.create(plantEntity, formRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormResponseDto> updateForm(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id, @RequestBody FormRequestDto formRequestDto) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(formService.update(id, plantEntity, formRequestDto));
    }

    @DeleteMapping("/{id}")
    public void deleteFormById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        formService.deleteById(id, plantEntity);
    }
}
