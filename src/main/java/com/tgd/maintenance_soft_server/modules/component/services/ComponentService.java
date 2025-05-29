package com.tgd.maintenance_soft_server.modules.component.services;

import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import com.tgd.maintenance_soft_server.modules.component.models.ComponentStatus;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.stereotype.Service;

@Service
public interface ComponentService {

    void deleteComponentById(Long id, PlantEntity plantEntity);

    ComponentResponseDto updateComponentStatus(Long id, ComponentStatus newStatus, PlantEntity plant);
}
