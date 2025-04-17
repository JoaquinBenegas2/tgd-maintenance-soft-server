package com.tgd.maintenance_soft_server.modules.sector.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import com.tgd.maintenance_soft_server.interfaces.BaseIdentifyingEntity;
import com.tgd.maintenance_soft_server.lib.blo_service.interfaces.IdentifyingEntity;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "SECTORS")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SectorEntity extends BaseIdentifyingEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private Boolean active;
}
