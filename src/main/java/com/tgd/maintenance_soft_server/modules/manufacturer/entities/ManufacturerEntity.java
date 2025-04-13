package com.tgd.maintenance_soft_server.modules.manufacturer.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MANUFACTURERS")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class  ManufacturerEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "country")
    private String country;

    @Column(name = "active")
    private Boolean active;
}
