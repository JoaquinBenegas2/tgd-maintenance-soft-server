package com.tgd.maintenance_soft_server.modules.form.services;

import com.tgd.maintenance_soft_server.lib.blo_service.interfaces.BloServiceInterface;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormRequestDto;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormResponseDto;
import com.tgd.maintenance_soft_server.modules.form.entities.FormEntity;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;

public interface FormService  extends BloServiceInterface<FormRequestDto, FormResponseDto, FormEntity, Long, PlantEntity> {
}
