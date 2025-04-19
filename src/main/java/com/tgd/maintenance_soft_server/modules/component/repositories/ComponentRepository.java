package com.tgd.maintenance_soft_server.modules.component.repositories;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.modules.component.entities.ComponentEntity;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ComponentRepository extends BloRepository<ComponentEntity, Long, PlantEntity>, JpaSpecificationExecutor<ComponentEntity> {

    List<ComponentEntity> findByAsset_IdAndIdentifyingEntity(Long assetId, PlantEntity plant);

    Optional<ComponentEntity> findByIdAndAsset_IdAndIdentifyingEntity(Long componentId, Long assetId, PlantEntity plant);
}
