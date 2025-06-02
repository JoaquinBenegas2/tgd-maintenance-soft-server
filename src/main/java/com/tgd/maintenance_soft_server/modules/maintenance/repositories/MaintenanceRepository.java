package com.tgd.maintenance_soft_server.modules.maintenance.repositories;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceEntity;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.route.entities.RouteEntity;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;


@Repository
public interface MaintenanceRepository extends BloRepository<MaintenanceEntity, Long, PlantEntity> {
    List<MaintenanceEntity> findAllByRouteIdAndElementIdAndMaintenanceDateGreaterThanEqualAndIdentifyingEntity(
            Long routeId,
            Long elementId,
            LocalDate maintenanceDate,
            PlantEntity identifyingEntity
    );

    List<MaintenanceEntity> findByMaintenanceDateAndIdentifyingEntity(LocalDate maintenanceDate, PlantEntity identifyingEntity);

    boolean existsByRoute(RouteEntity route);

    List<MaintenanceEntity> findAllByIdentifyingEntityAndElement_IdAndMaintenanceDateBetween(
            PlantEntity plant,
            Long elementId,
            LocalDate dateFrom,
            LocalDate dateTo
    );

    List<MaintenanceEntity> findAllByIdentifyingEntityAndElement_Component_IdAndMaintenanceDateBetween(
            PlantEntity plant,
            Long componentId,
            LocalDate dateFrom,
            LocalDate dateTo
    );

    List<MaintenanceEntity> findAllByIdentifyingEntityAndElement_Component_Asset_IdAndMaintenanceDateBetween(
            PlantEntity plant,
            Long assetId,
            LocalDate dateFrom,
            LocalDate dateTo
    );
}
