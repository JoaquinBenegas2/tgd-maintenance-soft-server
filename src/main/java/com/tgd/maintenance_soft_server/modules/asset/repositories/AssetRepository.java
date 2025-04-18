package com.tgd.maintenance_soft_server.modules.asset.repositories;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.modules.asset.entities.AssetEntity;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetRepository extends BloRepository<AssetEntity, Long, PlantEntity>, JpaSpecificationExecutor<AssetEntity> {
}
