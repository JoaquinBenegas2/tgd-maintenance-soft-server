package com.tgd.maintenance_soft_server.modules.asset.services.implementation;

import com.tgd.maintenance_soft_server.modules.asset.dtos.AssetRequestDto;
import com.tgd.maintenance_soft_server.modules.asset.dtos.AssetResponseDto;
import com.tgd.maintenance_soft_server.modules.asset.entities.AssetEntity;
import com.tgd.maintenance_soft_server.modules.asset.models.AssetStatus;
import com.tgd.maintenance_soft_server.modules.asset.repositories.AssetRepository;
import com.tgd.maintenance_soft_server.modules.asset.services.AssetService;
import com.tgd.maintenance_soft_server.modules.manufacturer.entities.ManufacturerEntity;
import com.tgd.maintenance_soft_server.modules.manufacturer.repositories.ManufacturerRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.sector.entities.SectorEntity;
import com.tgd.maintenance_soft_server.modules.sector.repositories.SectorRepository;
import com.tgd.maintenance_soft_server.spec.GenericSpecification;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AssetServiceImpl implements AssetService {

    private final AssetRepository assetRepository;
    private final ModelMapper modelMapper;

    private final GenericSpecification<AssetEntity> genericSpecification;
    private final SectorRepository sectorRepository;
    private final ManufacturerRepository manufacturerRepository;

    @Override
    public List<AssetResponseDto> getAllAssets(
            PlantEntity plantEntity,
            String sort,
            String direction,
            Map<String, Object> filters,
            String search
    ) {
        List<String> searchableFields = List.of("name", "description", "sector.name", "manufacturer.name", "model", "serialNumber", "status");
        filters.put("identifyingEntity.id", plantEntity.getId());

        Specification<AssetEntity> spec = genericSpecification.buildSpecification(filters, search, searchableFields);

        Sort sortOrder = Sort.by(
                Sort.Direction.fromString(direction != null ? direction : "ASC"),
                sort != null ? sort : "id"
        );

        List<AssetEntity> assetEntityList = assetRepository.findAll(spec, sortOrder);

        return assetEntityList.stream()
                .map(asset -> modelMapper.map(asset, AssetResponseDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Page<AssetResponseDto> getPagedAssets(PlantEntity plantEntity, Pageable pageable, Map<String, Object> filters, String search) {
        List<String> searchableFields = List.of("name", "description", "sector.name", "manufacturer.name", "model", "serialNumber", "status");
        filters.put("identifyingEntity.id", plantEntity.getId());

        Specification<AssetEntity> spec = genericSpecification.buildSpecification(filters, search, searchableFields);
        Page<AssetEntity> assetEntities = assetRepository.findAll(spec, pageable);

        return assetEntities.map(asset -> modelMapper.map(asset, AssetResponseDto.class));
    }

    @Override
    public AssetResponseDto getAssetById(Long id, PlantEntity plantEntity) {
        return assetRepository.findByIdAndIdentifyingEntity(id, plantEntity)
                .map(asset -> modelMapper.map(asset, AssetResponseDto.class))
                .orElseThrow(() -> new EntityNotFoundException("Asset not found or not associated with the plant"));
    }

    @Override
    public AssetResponseDto createAsset(PlantEntity plantEntity, AssetRequestDto assetRequestDto) {
        AssetEntity assetEntity = new AssetEntity();
        assetEntity.setName(assetRequestDto.getName());
        assetEntity.setDescription(assetRequestDto.getDescription());
        assetEntity.setModel(assetRequestDto.getModel());
        assetEntity.setSerialNumber(assetRequestDto.getSerialNumber());
        assetEntity.setStatus(AssetStatus.ACTIVE);
        assetEntity.setInstallationDate(assetRequestDto.getInstallationDate());

        SectorEntity sector = sectorRepository.findById(assetRequestDto.getSectorId())
                .orElseThrow(() -> new EntityNotFoundException("Sector not found"));

        ManufacturerEntity manufacturer = manufacturerRepository.findById(assetRequestDto.getManufacturerId())
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found"));

        assetEntity.setIdentifyingEntity(plantEntity);
        assetEntity.setSector(sector);
        assetEntity.setManufacturer(manufacturer);

        AssetEntity savedAsset = assetRepository.save(assetEntity);

        return modelMapper.map(savedAsset, AssetResponseDto.class);
    }

    @Override
    public AssetResponseDto updateAsset(Long id, PlantEntity plantEntity, AssetRequestDto assetRequestDto) {
        AssetEntity existingAsset = assetRepository.findByIdAndIdentifyingEntity(id, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found or not associated with the plant"));

        existingAsset.setName(assetRequestDto.getName());
        existingAsset.setDescription(assetRequestDto.getDescription());
        existingAsset.setModel(assetRequestDto.getModel());
        existingAsset.setSerialNumber(assetRequestDto.getSerialNumber());
        existingAsset.setStatus(AssetStatus.ACTIVE);
        existingAsset.setInstallationDate(assetRequestDto.getInstallationDate());

        SectorEntity sector = sectorRepository.findById(assetRequestDto.getSectorId())
                .orElseThrow(() -> new EntityNotFoundException("Sector not found"));

        ManufacturerEntity manufacturer = manufacturerRepository.findById(assetRequestDto.getManufacturerId())
                .orElseThrow(() -> new EntityNotFoundException("Manufacturer not found"));

        existingAsset.setIdentifyingEntity(plantEntity);
        existingAsset.setSector(sector);
        existingAsset.setManufacturer(manufacturer);

        AssetEntity savedAsset = assetRepository.save(existingAsset);

        return modelMapper.map(savedAsset, AssetResponseDto.class);
    }

    @Override
    @Transactional
    public void deleteAssetById(Long id, PlantEntity plantEntity) {
        AssetEntity existingAsset = assetRepository.findByIdAndIdentifyingEntity(id, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found or not associated with the plant"));

        assetRepository.delete(existingAsset);
    }
}
