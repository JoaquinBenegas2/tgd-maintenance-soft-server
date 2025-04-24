package com.tgd.maintenance_soft_server.modules.manufacturer.services;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.manufacturer.dtos.ManufacturerRequestDto;
import com.tgd.maintenance_soft_server.modules.manufacturer.dtos.ManufacturerResponseDto;
import com.tgd.maintenance_soft_server.modules.manufacturer.repositories.ManufacturerRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.manufacturer.entities.ManufacturerEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
}
