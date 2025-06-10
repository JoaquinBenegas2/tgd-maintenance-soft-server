package com.tgd.maintenance_soft_server.modules.maintenance.repositories;

import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceAnswerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MaintenanceAnswerRepository extends JpaRepository<MaintenanceAnswerEntity, Long> {
}
