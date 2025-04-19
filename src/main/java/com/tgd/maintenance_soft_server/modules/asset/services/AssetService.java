package com.tgd.maintenance_soft_server.modules.asset.services;

import com.tgd.maintenance_soft_server.modules.asset.dtos.AssetRequestDto;
import com.tgd.maintenance_soft_server.modules.asset.dtos.AssetResponseDto;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface AssetService {

    List<AssetResponseDto> getAllAssets(
            PlantEntity plantEntity,
            String sort,
            String direction,
            Map<String, Object> filters,
            String search
    );

    Page<AssetResponseDto> getPagedAssets(
            PlantEntity plantEntity,
            Pageable pageable,
            Map<String, Object> filters,
            String search
    );

    AssetResponseDto getAssetById(Long id, PlantEntity plantEntity);

    AssetResponseDto createAsset(PlantEntity plantEntity, AssetRequestDto assetRequestDto);

    AssetResponseDto updateAsset(Long id, PlantEntity plantEntity, AssetRequestDto assetRequestDto);

    void deleteAssetById(Long id, PlantEntity plantEntity);
}
