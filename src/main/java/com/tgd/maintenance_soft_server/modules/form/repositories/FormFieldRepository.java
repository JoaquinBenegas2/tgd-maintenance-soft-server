package com.tgd.maintenance_soft_server.modules.form.repositories;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.modules.form.entities.FormFieldEntity;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface FormFieldRepository extends BloRepository<FormFieldEntity, Long, PlantEntity>, JpaSpecificationExecutor<FormFieldEntity> {

}
