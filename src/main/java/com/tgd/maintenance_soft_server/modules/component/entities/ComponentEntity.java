package com.tgd.maintenance_soft_server.modules.component.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import com.tgd.maintenance_soft_server.interfaces.BaseIdentifyingEntity;
import com.tgd.maintenance_soft_server.modules.asset.entities.AssetEntity;
import com.tgd.maintenance_soft_server.modules.component.models.ComponentStatus;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.manufacturer.entities.ManufacturerEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COMPONENTS")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ComponentEntity extends BaseIdentifyingEntity {

    public static Object ComponentStatus;
    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_id", nullable = false)
    private AssetEntity asset;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerEntity manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private ComponentStatus status;

    @OneToMany(mappedBy = "component", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ElementEntity> elements = new ArrayList<>();
}
