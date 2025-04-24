package com.tgd.maintenance_soft_server.modules.sector.services;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.sector.dtos.SectorRequestDto;
import com.tgd.maintenance_soft_server.modules.sector.dtos.SectorResponseDto;
import com.tgd.maintenance_soft_server.modules.sector.entities.SectorEntity;
import com.tgd.maintenance_soft_server.modules.sector.repositories.SectorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
