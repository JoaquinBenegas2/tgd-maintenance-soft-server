package com.tgd.maintenance_soft_server.modules.sector.controllers;

import com.tgd.maintenance_soft_server.dtos.active_status.ActiveStatusRequestDto;
import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.sector.dtos.SectorRequestDto;
import com.tgd.maintenance_soft_server.modules.sector.dtos.SectorResponseDto;
import com.tgd.maintenance_soft_server.modules.sector.services.SectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sectors")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class SectorController {

    private final AuthService authService;
    private final SectorService sectorService;

    @GetMapping
    public ResponseEntity<List<SectorResponseDto>> getSectorList(
            @RequestHeader("x-plant-id") Long plantId,
            @RequestParam(value = "active", required = false) Boolean active
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);

        if (active != null) {
            return ResponseEntity.ok(sectorService.getAllByActive(plantEntity, active));
        }

        return ResponseEntity.ok(sectorService.getAll(plantEntity));
    }


    @GetMapping("/{id}")
    public ResponseEntity<SectorResponseDto> getSectorById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(sectorService.getById(id, plantEntity));
    }

    @PostMapping
    public ResponseEntity<SectorResponseDto> createSector(@RequestHeader("x-plant-id") Long plantId, @RequestBody SectorRequestDto sectorRequestDto) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(sectorService.create(plantEntity, sectorRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SectorResponseDto> updateSector(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id, @RequestBody SectorRequestDto sectorRequestDto) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(sectorService.update(id, plantEntity, sectorRequestDto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SectorResponseDto> updateSectorActive(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long id,
            @RequestBody ActiveStatusRequestDto statusRequestDto
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(sectorService.updateSectorActive(id, plantEntity, statusRequestDto.getActive()));
    }

    @DeleteMapping("/{id}")
    public void deleteSectorById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        sectorService.deleteById(id, plantEntity);
    }
}
