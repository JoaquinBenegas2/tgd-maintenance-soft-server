package com.tgd.maintenance_soft_server.modules.manufacturer.services;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.manufacturer.dtos.ManufacturerRequestDto;
import com.tgd.maintenance_soft_server.modules.manufacturer.dtos.ManufacturerResponseDto;
import com.tgd.maintenance_soft_server.modules.manufacturer.repositories.ManufacturerRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.manufacturer.entities.ManufacturerEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ManufacturerService extends BloService<
        ManufacturerRequestDto,
        ManufacturerResponseDto,
        ManufacturerEntity,
        Long,
        PlantEntity> {
    private final ManufacturerRepository manufacturerRepository;

    @Override
    public ManufacturerRepository getRepository() {
        return manufacturerRepository;
    }

    @Override
    protected ManufacturerEntity toEntity(ManufacturerRequestDto request) {
        ManufacturerEntity manufacturerEntity = modelMapper.map(request, ManufacturerEntity.class);
        manufacturerEntity.setActive(true);

        return manufacturerEntity;
    }

    public List<ManufacturerResponseDto> getAllByActive(PlantEntity plantEntity, Boolean active) {
        return manufacturerRepository.findAllByIdentifyingEntityAndActive(plantEntity, active)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public ManufacturerResponseDto updateManufacturerActive(Long id, PlantEntity plantEntity, Boolean active) {
        ManufacturerEntity manufacturerEntity = manufacturerRepository.findByIdAndIdentifyingEntity(id, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Entity not found or not associated with the identifying entity"));
        manufacturerEntity.setActive(active);

        manufacturerRepository.save(manufacturerEntity);
        return toDto(manufacturerEntity);
    }
}
