package com.tgd.maintenance_soft_server.modules.asset.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import com.tgd.maintenance_soft_server.interfaces.BaseIdentifyingEntity;
import com.tgd.maintenance_soft_server.modules.asset.models.AssetStatus;
import com.tgd.maintenance_soft_server.modules.component.entities.ComponentEntity;
import com.tgd.maintenance_soft_server.modules.manufacturer.entities.ManufacturerEntity;
import com.tgd.maintenance_soft_server.modules.sector.entities.SectorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "ASSETS")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssetEntity extends BaseIdentifyingEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "sector_id")
    private SectorEntity sector;

    @ManyToOne
    @JoinColumn(name = "manufacturer_id")
    private ManufacturerEntity manufacturer;

    @Column(name = "model")
    private String model;

    @Column(name = "serial_number")
    private String serialNumber;

    @Enumerated(EnumType.STRING)
    private AssetStatus status;

    @Column(name = "installation_date")
    private LocalDate installationDate;

    @OneToMany(mappedBy = "asset", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComponentEntity> components = new ArrayList<>();
}
