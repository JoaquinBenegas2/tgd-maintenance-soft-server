package com.tgd.maintenance_soft_server.modules.company.repositories;

import com.tgd.maintenance_soft_server.modules.company.entities.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CompanyRepository extends JpaRepository<CompanyEntity, Long> {
}
