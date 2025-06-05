package com.tgd.maintenance_soft_server.modules.route.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseIdentifyingEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "ROUTE_NOTIFICATION_STATUS")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteNotificationStatusEntity extends BaseIdentifyingEntity {

    @OneToOne(fetch = FetchType.LAZY)
    private RouteEntity route;

    @Column(name = "notified_at")
    private LocalDate lastNotifiedDate;
}
