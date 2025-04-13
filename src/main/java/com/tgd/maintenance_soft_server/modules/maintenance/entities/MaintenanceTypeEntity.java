package com.tgd.maintenance_soft_server.modules.maintenance.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MAINTENANCE_TYPES")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceTypeEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;
}
