package com.tgd.maintenance_soft_server.modules.sector.repositories;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.sector.entities.SectorEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface SectorRepository extends BloRepository<SectorEntity, Long, PlantEntity> {
}
