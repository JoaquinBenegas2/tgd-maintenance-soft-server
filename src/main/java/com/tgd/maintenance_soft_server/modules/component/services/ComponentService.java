package com.tgd.maintenance_soft_server.modules.component.services;

import com.tgd.maintenance_soft_server.lib.blo_service.interfaces.BloServiceInterface;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentRequestDto;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import com.tgd.maintenance_soft_server.modules.component.entities.ComponentEntity;
import com.tgd.maintenance_soft_server.modules.component.models.ComponentStatus;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ComponentService extends BloServiceInterface<ComponentRequestDto, ComponentResponseDto, ComponentEntity, Long, PlantEntity> {

    List<ComponentResponseDto> getAllByStatus(PlantEntity plantEntity, ComponentStatus status);

    void deleteComponentById(Long id, PlantEntity plantEntity);

    ComponentResponseDto updateComponentStatus(Long id, ComponentStatus newStatus, PlantEntity plant);
}
