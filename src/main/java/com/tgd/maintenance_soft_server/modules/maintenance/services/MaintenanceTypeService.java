package com.tgd.maintenance_soft_server.modules.maintenance.services;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceTypeRequestDto;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceTypeResponseDto;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceTypeEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.repositories.MaintenanceTypeRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MaintenanceTypeService extends BloService<
        MaintenanceTypeRequestDto,
        MaintenanceTypeResponseDto,
        MaintenanceTypeEntity,
        Long,
        PlantEntity> {
    private final MaintenanceTypeRepository maintenanceTypeRepository;

    @Override
    public MaintenanceTypeRepository getRepository() {
        return maintenanceTypeRepository;
    }
}
