package com.tgd.maintenance_soft_server.modules.route.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.route.models.RouteStatus;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ROUTES")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RouteEntity extends BaseEntity {

    @Column(name = "name")
    private  String name;

    @Column(name = "description")
    private String description;

    @Column(name = "periodicity_in_days")
    private Integer periodicityInDays;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Enumerated(EnumType.STRING)
    private RouteStatus status;

    @ManyToMany
    @JoinTable(
            name = "ROUTE_ELEMENTS",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "element_id")
    )
    private List<ElementEntity> assignedElements = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "ROUTE_OPERATORS",
            joinColumns = @JoinColumn(name = "route_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id")
    )
    private List<UserEntity> assignedOperators = new ArrayList<>();
}
