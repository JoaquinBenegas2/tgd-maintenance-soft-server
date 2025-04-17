package com.tgd.maintenance_soft_server.modules.plant.repositories;

import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantRepository extends JpaRepository<PlantEntity, Long> {
}
