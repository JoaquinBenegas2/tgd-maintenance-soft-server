package com.tgd.maintenance_soft_server.modules.manufacturer.repositories;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.manufacturer.entities.ManufacturerEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface ManufacturerRepository extends BloRepository<ManufacturerEntity, Long, PlantEntity> {
}
