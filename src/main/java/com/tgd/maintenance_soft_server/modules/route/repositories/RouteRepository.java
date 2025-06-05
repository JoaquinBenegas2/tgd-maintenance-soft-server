package com.tgd.maintenance_soft_server.modules.route.repositories;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.route.entities.RouteEntity;
import com.tgd.maintenance_soft_server.modules.route.models.RouteStatus;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RouteRepository extends BloRepository<RouteEntity, Long, PlantEntity> {
    List<RouteEntity> findAllByStatusIsAndIdentifyingEntity(RouteStatus status, PlantEntity plantEntity);

    List<RouteEntity> findAllByIdentifyingEntityAndStatus(PlantEntity plant, RouteStatus status);
}
