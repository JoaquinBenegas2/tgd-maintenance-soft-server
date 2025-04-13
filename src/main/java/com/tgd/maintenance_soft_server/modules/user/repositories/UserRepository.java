package com.tgd.maintenance_soft_server.modules.user.repositories;

import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByAuth0Id(String auth0Id);
}
