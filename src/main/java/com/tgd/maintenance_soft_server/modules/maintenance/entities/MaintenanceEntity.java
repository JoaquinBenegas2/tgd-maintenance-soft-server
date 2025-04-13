package com.tgd.maintenance_soft_server.modules.maintenance.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.route.entities.RouteEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "MAINTENANCES")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private RouteEntity route;

    @ManyToOne
    @JoinColumn(name = "element_id")
    private ElementEntity element;

    @Column(name = "maintenance_date")
    private LocalDate maintenanceDate;

    @OneToMany(mappedBy = "maintenance")
    private List<MaintenanceAnswerEntity> answers = new ArrayList<>();
}
