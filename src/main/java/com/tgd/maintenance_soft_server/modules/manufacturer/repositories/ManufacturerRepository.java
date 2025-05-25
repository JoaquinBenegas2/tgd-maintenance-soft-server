package com.tgd.maintenance_soft_server.modules.manufacturer.repositories;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.modules.manufacturer.entities.ManufacturerEntity;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ManufacturerRepository extends BloRepository<ManufacturerEntity, Long, PlantEntity> {
    List<ManufacturerEntity> findAllByIdentifyingEntityAndActive(PlantEntity plantEntity, Boolean active);
}
