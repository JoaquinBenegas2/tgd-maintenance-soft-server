package com.tgd.maintenance_soft_server.modules.form.services;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormRequestDto;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormResponseDto;
import com.tgd.maintenance_soft_server.modules.form.entities.FormEntity;
import com.tgd.maintenance_soft_server.modules.form.repositories.FormRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FormService extends BloService<
        FormRequestDto,
        FormResponseDto,
        FormEntity,
        Long,
        PlantEntity> {
    private final FormRepository formRepository;

    @Override
    public FormRepository getRepository() {
        return formRepository;
    }
}
