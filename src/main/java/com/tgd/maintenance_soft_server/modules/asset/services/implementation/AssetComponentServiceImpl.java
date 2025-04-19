package com.tgd.maintenance_soft_server.modules.asset.services.implementation;

import com.tgd.maintenance_soft_server.modules.asset.entities.AssetEntity;
import com.tgd.maintenance_soft_server.modules.asset.repositories.AssetRepository;
import com.tgd.maintenance_soft_server.modules.asset.services.AssetComponentService;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentRequestDto;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import com.tgd.maintenance_soft_server.modules.component.entities.ComponentEntity;
import com.tgd.maintenance_soft_server.modules.component.models.ComponentStatus;
import com.tgd.maintenance_soft_server.modules.component.repositories.ComponentRepository;
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
public class AssetComponentServiceImpl implements AssetComponentService {

    private final ComponentRepository componentRepository;
    private final ModelMapper modelMapper;
    private final AssetRepository assetRepository;
    private final ManufacturerRepository manufacturerRepository;

    @Override
    public List<ComponentResponseDto> getAllComponentsByAssetId(Long assetId, PlantEntity plantEntity) {
        return componentRepository.findByAsset_IdAndIdentifyingEntity(assetId, plantEntity)
                .stream()
                .map(component -> modelMapper.map(component, ComponentResponseDto.class))
                .toList();
    }

    @Override
    public ComponentResponseDto getComponentByIdAndAssetId(Long assetId, Long componentId, PlantEntity plantEntity) {
        ComponentEntity component = componentRepository
                .findByIdAndAsset_IdAndIdentifyingEntity(componentId, assetId, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Component not found"));

        return modelMapper.map(component, ComponentResponseDto.class);
    }

    @Override
    public ComponentResponseDto createAssetComponent(Long assetId, PlantEntity plantEntity, ComponentRequestDto componentRequestDto) {
        ComponentEntity componentEntity = new ComponentEntity();
        componentEntity.setName(componentRequestDto.getName());
        componentEntity.setDescription(componentRequestDto.getDescription());
        componentEntity.setModel(componentRequestDto.getModel());
        componentEntity.setSerialNumber(componentRequestDto.getSerialNumber());
        componentEntity.setStatus(ComponentStatus.ACTIVE);

        AssetEntity asset = assetRepository.findByIdAndIdentifyingEntity(assetId, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found"));

        ManufacturerEntity manufacturer = manufacturerRepository.findByIdAndIdentifyingEntity(componentRequestDto.getManufacturerId(), plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found"));

        componentEntity.setManufacturer(manufacturer);
        componentEntity.setAsset(asset);
        componentEntity.setIdentifyingEntity(plantEntity);

        componentRepository.save(componentEntity);

        return modelMapper.map(componentEntity, ComponentResponseDto.class);
    }

    @Override
    public ComponentResponseDto updateAssetComponent(Long assetId, Long componentId, PlantEntity plantEntity, ComponentRequestDto componentRequestDto) {
        ComponentEntity componentEntity = componentRepository.findByIdAndAsset_IdAndIdentifyingEntity(componentId, assetId, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Component not found"));

        componentEntity.setName(componentRequestDto.getName());
        componentEntity.setDescription(componentRequestDto.getDescription());
        componentEntity.setModel(componentRequestDto.getModel());
        componentEntity.setSerialNumber(componentRequestDto.getSerialNumber());
        componentEntity.setStatus(ComponentStatus.ACTIVE);

        ManufacturerEntity manufacturer = manufacturerRepository.findByIdAndIdentifyingEntity(componentRequestDto.getManufacturerId(), plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found"));

        componentEntity.setManufacturer(manufacturer);

        componentRepository.save(componentEntity);

        return modelMapper.map(componentEntity, ComponentResponseDto.class);
    }
}
