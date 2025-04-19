package com.tgd.maintenance_soft_server.modules.asset.controllers;

import com.tgd.maintenance_soft_server.modules.asset.dtos.AssetResponseDto;
import com.tgd.maintenance_soft_server.modules.asset.services.AssetComponentService;
import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentRequestDto;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets/{assetId}/components")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AssetComponentController {

    private final AuthService authService;
    private final AssetComponentService assetComponentService;

    @GetMapping
    public ResponseEntity<List<ComponentResponseDto>> getAllComponentsByAssetId(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long assetId
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(assetComponentService.getAllComponentsByAssetId(assetId, plantEntity));
    }

    @GetMapping("/{componentId}")
    public ResponseEntity<ComponentResponseDto> getComponentByIdAndAssetId(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long assetId,
            @PathVariable Long componentId
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(assetComponentService.getComponentByIdAndAssetId(assetId, componentId, plantEntity));
    }

    @PostMapping
    public ResponseEntity<ComponentResponseDto> createAssetComponent(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long assetId,
            @RequestBody ComponentRequestDto componentRequestDto
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(assetComponentService.createAssetComponent(assetId, plantEntity, componentRequestDto));
    }

    @PutMapping("/{componentId}")
    public ResponseEntity<ComponentResponseDto> updateAssetComponent(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long assetId,
            @PathVariable Long componentId,
            @RequestBody ComponentRequestDto componentRequestDto
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(assetComponentService.updateAssetComponent(assetId, componentId, plantEntity, componentRequestDto));
    }
}
