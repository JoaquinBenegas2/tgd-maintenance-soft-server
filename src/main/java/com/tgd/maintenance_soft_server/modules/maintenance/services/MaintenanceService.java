package com.tgd.maintenance_soft_server.modules.maintenance.services;

import com.tgd.maintenance_soft_server.lib.blo_service.interfaces.BloServiceInterface;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceRequestDto;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceResponseDto;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceUpdateRequestDto;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceEntity;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import org.springframework.stereotype.Service;

@Service
public interface MaintenanceService extends BloServiceInterface<MaintenanceRequestDto, MaintenanceResponseDto, MaintenanceEntity, Long, PlantEntity> {

    MaintenanceResponseDto createMaintenance(UserEntity userEntity, PlantEntity plantEntity, MaintenanceRequestDto maintenanceRequestDto);

    MaintenanceResponseDto updateMaintenance(Long maintenanceId, PlantEntity plantEntity, MaintenanceUpdateRequestDto updateDto);
}
