package com.tgd.maintenance_soft_server.modules.form.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceTypeEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FORMS")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "maintenance_type_id")
    private MaintenanceTypeEntity maintenanceType;

    @OneToMany(mappedBy = "form")
    private List<FormFieldEntity> options = new ArrayList<>();
}
