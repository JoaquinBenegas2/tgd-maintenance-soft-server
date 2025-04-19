package com.tgd.maintenance_soft_server.modules.asset.services;

import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentRequestDto;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssetComponentService {

    List<ComponentResponseDto> getAllComponentsByAssetId(Long assetId, PlantEntity plantEntity);

    ComponentResponseDto getComponentByIdAndAssetId(Long assetId, Long componentId, PlantEntity plantEntity);

    ComponentResponseDto createAssetComponent(Long assetId, PlantEntity plantEntity, ComponentRequestDto componentRequestDto);

    ComponentResponseDto updateAssetComponent(Long assetId, Long componentId, PlantEntity plantEntity, ComponentRequestDto componentRequestDto);
}
