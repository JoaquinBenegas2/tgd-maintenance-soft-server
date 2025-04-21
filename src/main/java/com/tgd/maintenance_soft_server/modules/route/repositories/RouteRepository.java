package com.tgd.maintenance_soft_server.modules.route.repositories;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.route.entities.RouteEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends BloRepository<RouteEntity, Long, PlantEntity> {
}
