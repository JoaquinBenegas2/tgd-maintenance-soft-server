package com.tgd.maintenance_soft_server.modules.sector.services;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.sector.dtos.SectorRequestDto;
import com.tgd.maintenance_soft_server.modules.sector.dtos.SectorResponseDto;
import com.tgd.maintenance_soft_server.modules.sector.entities.SectorEntity;
import com.tgd.maintenance_soft_server.modules.sector.repositories.SectorRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SectorService extends BloService<
        SectorRequestDto,
        SectorResponseDto,
        SectorEntity,
        Long,
        PlantEntity> {
    private final SectorRepository sectorRepository;

    @Override
    public SectorRepository getRepository() {
        return sectorRepository;
    }

    @Override
    protected SectorEntity toEntity(SectorRequestDto request) {
        SectorEntity sectorEntity = modelMapper.map(request, SectorEntity.class);
        sectorEntity.setActive(true);

        return sectorEntity;
    }

    public List<SectorResponseDto> getAllByActive(PlantEntity plantEntity, Boolean active) {
        return sectorRepository.findAllByIdentifyingEntityAndActive(plantEntity, active)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public SectorResponseDto updateSectorActive(Long id, PlantEntity plantEntity, Boolean active) {
        SectorEntity sectorEntity = sectorRepository.findByIdAndIdentifyingEntity(id, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Entity not found or not associated with the identifying entity"));
        sectorEntity.setActive(active);

        sectorRepository.save(sectorEntity);
        return toDto(sectorEntity);
    }
}
