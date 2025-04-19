package com.tgd.maintenance_soft_server.modules.asset.controllers;

import com.tgd.maintenance_soft_server.modules.asset.services.AssetComponentElementService;
import com.tgd.maintenance_soft_server.modules.asset.services.AssetComponentService;
import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentRequestDto;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementRequestDto;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/assets/{assetId}/components/{componentId}/elements")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AssetComponentElementController {

    private final AuthService authService;
    private final AssetComponentElementService assetComponentElementService;

    @GetMapping
    public ResponseEntity<List<ElementResponseDto>> getAllComponentsByAssetId(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long assetId,
            @PathVariable Long componentId
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(assetComponentElementService.getAllElementsByComponentIdAndAssetId(assetId, componentId, plantEntity));
    }

    @GetMapping("/{elementId}")
    public ResponseEntity<ElementResponseDto> getComponentByIdAndAssetId(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long assetId,
            @PathVariable Long componentId,
            @PathVariable Long elementId
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(assetComponentElementService.getElementByIdComponentIdAndAssetId(assetId, componentId, elementId, plantEntity));
    }

    @PostMapping
    public ResponseEntity<ElementResponseDto> createAssetComponentElement(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long assetId,
            @PathVariable Long componentId,
            @RequestBody ElementRequestDto elementRequestDto
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(assetComponentElementService.createAssetComponentElement(assetId, componentId, plantEntity, elementRequestDto));
    }

    @PutMapping("/{elementId}")
    public ResponseEntity<ElementResponseDto> updateAssetComponentElement(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long assetId,
            @PathVariable Long componentId,
            @PathVariable Long elementId,
            @RequestBody ElementRequestDto elementRequestDto
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(assetComponentElementService.updateAssetComponentElement(assetId, componentId, elementId, plantEntity, elementRequestDto));
    }
}
