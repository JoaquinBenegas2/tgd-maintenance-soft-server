package com.tgd.maintenance_soft_server.modules.asset.services;

import com.tgd.maintenance_soft_server.modules.element.dtos.ElementRequestDto;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementResponseDto;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssetComponentElementService {

    List<ElementResponseDto> getAllElementsByComponentIdAndAssetId(Long assetId, Long componentId, PlantEntity plantEntity);

    ElementResponseDto getElementByIdComponentIdAndAssetId(Long assetId, Long componentId, Long elementId, PlantEntity plantEntity);

    ElementResponseDto createAssetComponentElement(Long assetId, Long componentId, PlantEntity plantEntity, ElementRequestDto componentRequestDto);

    ElementResponseDto updateAssetComponentElement(Long assetId, Long componentId, Long elementId, PlantEntity plantEntity, ElementRequestDto componentRequestDto);
}
