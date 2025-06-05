package com.tgd.maintenance_soft_server.modules.route.repositories;

import com.tgd.maintenance_soft_server.modules.route.entities.RouteEntity;
import com.tgd.maintenance_soft_server.modules.route.entities.RouteNotificationStatusEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RouteNotificationStatusRepository extends JpaRepository<RouteNotificationStatusEntity, Long> {
    Optional<RouteNotificationStatusEntity> findByRoute(RouteEntity route);
}
