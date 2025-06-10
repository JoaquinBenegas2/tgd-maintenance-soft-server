package com.tgd.maintenance_soft_server.modules.form.repositories;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.modules.form.entities.FormEntity;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface FormRepository extends BloRepository<FormEntity, Long, PlantEntity>, JpaSpecificationExecutor<FormEntity> {
    List<FormEntity> findAllByMaintenanceTypeIdInAndIdentifyingEntity(
            List<Long> maintenanceTypeIds,
            PlantEntity plant
    );
}
