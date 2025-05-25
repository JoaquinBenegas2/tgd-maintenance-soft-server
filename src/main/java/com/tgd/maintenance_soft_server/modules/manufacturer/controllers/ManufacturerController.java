package com.tgd.maintenance_soft_server.modules.manufacturer.controllers;

import com.tgd.maintenance_soft_server.dtos.active_status.ActiveStatusRequestDto;
import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.manufacturer.dtos.ManufacturerRequestDto;
import com.tgd.maintenance_soft_server.modules.manufacturer.dtos.ManufacturerResponseDto;
import com.tgd.maintenance_soft_server.modules.manufacturer.services.ManufacturerService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/manufacturers")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class ManufacturerController {

    private final AuthService authService;
    private final ManufacturerService manufacturerService;

    @GetMapping
    public ResponseEntity<List<ManufacturerResponseDto>> getManufacturerList(
            @RequestHeader("x-plant-id") Long plantId,
            @RequestParam(value = "active", required = false) Boolean active
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);

        if (active != null) {
            return ResponseEntity.ok(manufacturerService.getAllByActive(plantEntity, active));
        }

        return ResponseEntity.ok(manufacturerService.getAll(plantEntity));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManufacturerResponseDto> getManufacturerById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(manufacturerService.getById(id, plantEntity));
    }

    @PostMapping
    public ResponseEntity<ManufacturerResponseDto> createManufacturer(@RequestHeader("x-plant-id") Long plantId, @RequestBody ManufacturerRequestDto manufacturerRequestDto) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(manufacturerService.create(plantEntity, manufacturerRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ManufacturerResponseDto> updateManufacturer(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id, @RequestBody ManufacturerRequestDto manufacturerRequestDto) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(manufacturerService.update(id, plantEntity, manufacturerRequestDto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ManufacturerResponseDto> updateManufacturerActive(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long id,
            @RequestBody ActiveStatusRequestDto statusRequestDto
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(manufacturerService.updateManufacturerActive(id, plantEntity, statusRequestDto.getActive()));
    }

    @DeleteMapping("/{id}")
    public void deleteManufacturerById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        manufacturerService.deleteById(id, plantEntity);
    }
}
