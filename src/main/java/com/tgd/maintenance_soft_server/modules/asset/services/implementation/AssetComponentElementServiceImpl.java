package com.tgd.maintenance_soft_server.modules.asset.services.implementation;

import com.tgd.maintenance_soft_server.modules.asset.services.AssetComponentElementService;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentRequestDto;
import com.tgd.maintenance_soft_server.modules.component.entities.ComponentEntity;
import com.tgd.maintenance_soft_server.modules.component.repositories.ComponentRepository;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementRequestDto;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementResponseDto;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.element.models.ElementStatus;
import com.tgd.maintenance_soft_server.modules.element.repositories.ElementRepository;
import com.tgd.maintenance_soft_server.modules.manufacturer.entities.ManufacturerEntity;
import com.tgd.maintenance_soft_server.modules.manufacturer.repositories.ManufacturerRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AssetComponentElementServiceImpl implements AssetComponentElementService {

    private final ElementRepository elementRepository;
    private final ModelMapper modelMapper;
    private final ComponentRepository componentRepository;
    private final ManufacturerRepository manufacturerRepository;

    @Override
    public List<ElementResponseDto> getAllElementsByComponentIdAndAssetId(Long assetId, Long componentId, PlantEntity plantEntity) {
        return elementRepository
                .findByComponent_Asset_IdAndComponent_IdAndIdentifyingEntity(assetId, componentId, plantEntity)
                .stream()
                .map(elementEntity -> modelMapper.map(elementEntity, ElementResponseDto.class))
                .toList();
    }

    @Override
    public ElementResponseDto getElementByIdComponentIdAndAssetId(Long assetId, Long componentId, Long elementId, PlantEntity plantEntity) {
        ElementEntity element = elementRepository
                .findByComponent_Asset_IdAndComponent_IdAndIdAndIdentifyingEntity(assetId, componentId, elementId, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Element not found"));

        return modelMapper.map(element, ElementResponseDto.class);
    }

    @Override
    public ElementResponseDto createAssetComponentElement(Long assetId, Long componentId, PlantEntity plantEntity, ElementRequestDto componentRequestDto) {
        ElementEntity elementEntity = new ElementEntity();
        elementEntity.setName(componentRequestDto.getName());
        elementEntity.setDescription(componentRequestDto.getDescription());
        elementEntity.setStatus(ElementStatus.ACTIVE);

        ComponentEntity component = componentRepository.findByIdAndAsset_IdAndIdentifyingEntity(componentId, assetId, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Component not found"));

        ManufacturerEntity manufacturer = manufacturerRepository.findByIdAndIdentifyingEntity(componentRequestDto.getManufacturerId(), plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found"));

        elementEntity.setManufacturer(manufacturer);
        elementEntity.setComponent(component);
        elementEntity.setIdentifyingEntity(plantEntity);

        elementRepository.save(elementEntity);

        return modelMapper.map(elementEntity, ElementResponseDto.class);
    }

    @Override
    public ElementResponseDto updateAssetComponentElement(Long assetId, Long componentId, Long elementId, PlantEntity plantEntity, ElementRequestDto componentRequestDto) {
        ElementEntity elementEntity = elementRepository.findByComponent_Asset_IdAndComponent_IdAndIdAndIdentifyingEntity(assetId, componentId, elementId, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Element not found"));

        elementEntity.setName(componentRequestDto.getName());
        elementEntity.setDescription(componentRequestDto.getDescription());
        elementEntity.setStatus(ElementStatus.ACTIVE);

        ManufacturerEntity manufacturer = manufacturerRepository.findByIdAndIdentifyingEntity(componentRequestDto.getManufacturerId(), plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found"));

        elementRepository.save(elementEntity);

        return modelMapper.map(elementEntity, ElementResponseDto.class);
    }
}
