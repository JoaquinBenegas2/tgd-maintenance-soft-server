package com.tgd.maintenance_soft_server.modules.element.repositories;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.modules.component.entities.ComponentEntity;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.element.models.ElementStatus;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import javax.swing.text.Element;
import java.util.List;
import java.util.Optional;

@Repository
public interface ElementRepository extends BloRepository<ElementEntity, Long, PlantEntity>, JpaSpecificationExecutor<ElementEntity> {

    List<ElementEntity> findByComponent_Asset_IdAndComponent_IdAndIdentifyingEntity(
            Long assetId, Long componentId, PlantEntity plant
    );

    Optional<ElementEntity> findByComponent_Asset_IdAndComponent_IdAndIdAndIdentifyingEntity(
            Long assetId, Long componentId, Long elementId, PlantEntity plant
    );

    List<ElementEntity> findAllByIdentifyingEntityAndStatus(PlantEntity plant, ElementStatus status);
}
