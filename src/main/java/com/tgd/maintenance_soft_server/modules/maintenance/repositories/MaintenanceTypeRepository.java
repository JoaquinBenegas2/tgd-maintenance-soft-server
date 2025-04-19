package com.tgd.maintenance_soft_server.modules.maintenance.repositories;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceTypeEntity;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceTypeRepository extends BloRepository<MaintenanceTypeEntity, Long, PlantEntity> {
}
