package com.tgd.maintenance_soft_server.modules.element.services;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementRequestDto;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementResponseDto;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.element.repositories.ElementRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElementService extends BloService<
        ElementRequestDto,
        ElementResponseDto,
        ElementEntity,
        Long,
        PlantEntity> {
    private final ElementRepository elementRepository;

    @Override
    public ElementRepository getRepository() {
        return elementRepository;
    }
}
