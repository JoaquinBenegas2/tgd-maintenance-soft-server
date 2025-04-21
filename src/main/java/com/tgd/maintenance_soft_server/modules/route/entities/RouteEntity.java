package com.tgd.maintenance_soft_server.modules.route.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import com.tgd.maintenance_soft_server.interfaces.BaseIdentifyingEntity;
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
public class RouteEntity extends BaseIdentifyingEntity {

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

    public void assignUser(UserEntity user) {
        if (!assignedOperators.contains(user)) {
            assignedOperators.add(user);
            user.getRoutes().add(this);
        }
    }

    public void unassignUser(UserEntity user) {
        if (assignedOperators.contains(user)) {
            assignedOperators.remove(user);
            user.getRoutes().remove(this);
        }
    }

    public void assignElement(ElementEntity element) {
        if (!assignedElements.contains(element)) {
            assignedElements.add(element);
            element.getRoutes().add(this);
        }
    }

    public void unassignElement(ElementEntity element) {
        if (assignedElements.contains(element)) {
            assignedElements.remove(element);
            element.getRoutes().remove(this);
        }
    }
}
