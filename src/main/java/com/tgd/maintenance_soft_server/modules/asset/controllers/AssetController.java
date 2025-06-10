package com.tgd.maintenance_soft_server.modules.asset.controllers;

import com.tgd.maintenance_soft_server.modules.asset.dtos.AssetRequestDto;
import com.tgd.maintenance_soft_server.modules.asset.dtos.AssetResponseDto;
import com.tgd.maintenance_soft_server.modules.asset.models.AssetStatus;
import com.tgd.maintenance_soft_server.modules.asset.services.AssetService;
import com.tgd.maintenance_soft_server.modules.auth.services.AuthService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/assets")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AssetController {

    private final AuthService authService;
    private final AssetService assetService;

    @GetMapping
    public ResponseEntity<Page<AssetResponseDto>> getAssetList(
            @RequestHeader("x-plant-id") Long plantId,
            @RequestParam(required = false) Integer page,
            @RequestParam(required = false) Integer size,
            @RequestParam(required = false, defaultValue = "id") String sort,
            @RequestParam(required = false, defaultValue = "ASC") String direction,
            @RequestParam(required = false) Map<String, Object> filters,
            @RequestParam(required = false) String search
    ) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);

        if (page == null && size == null) {
            List<AssetResponseDto> assetResponseDtoList = assetService.getAllAssets(plantEntity, sort, direction, filters, search);
            Pageable pageable = PageRequest.of(0, assetResponseDtoList.size());
            return ResponseEntity.ok(new PageImpl<>(assetResponseDtoList, pageable, assetResponseDtoList.size()));
        }

        int pageIndex = (page != null) ? page : 0;
        int pageSize = (size != null) ? size : 10;

        Pageable pageable = PageRequest.of(pageIndex, pageSize, Sort.Direction.fromString(direction), sort);

        Map<String, Object> filteredParams = filters.entrySet().stream()
                .filter(entry -> !List.of("page", "size", "sort", "search").contains(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));

        return ResponseEntity.ok(assetService.getPagedAssets(plantEntity, pageable, filteredParams, search));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetResponseDto> getAssetById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(assetService.getAssetById(id, plantEntity));
    }

    @PostMapping
    public ResponseEntity<AssetResponseDto> createAsset(@RequestHeader("x-plant-id") Long plantId, @RequestBody AssetRequestDto assetRequestDto) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(assetService.createAsset(plantEntity, assetRequestDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetResponseDto> updateAsset(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id, @RequestBody AssetRequestDto assetRequestDto) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(assetService.updateAsset(id, plantEntity, assetRequestDto));
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<AssetResponseDto> updateAssetStatus(
            @RequestHeader("x-plant-id") Long plantId,
            @PathVariable Long id,
            @RequestParam AssetStatus status) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        return ResponseEntity.ok(assetService.updateAssetStatus(id, status, plantEntity));
    }

    @DeleteMapping("/{id}")
    public void deleteAssetById(@RequestHeader("x-plant-id") Long plantId, @PathVariable Long id) {
        PlantEntity plantEntity = authService.getSelectedPlant(plantId);
        assetService.deleteAssetById(id, plantEntity);
    }
}
