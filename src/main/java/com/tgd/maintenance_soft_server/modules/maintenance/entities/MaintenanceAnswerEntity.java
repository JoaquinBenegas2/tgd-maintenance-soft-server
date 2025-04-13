package com.tgd.maintenance_soft_server.modules.maintenance.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import com.tgd.maintenance_soft_server.modules.form.entities.FormEntity;
import com.tgd.maintenance_soft_server.modules.form.entities.FormFieldEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "MAINTENANCE_ANSWERS")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MaintenanceAnswerEntity extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "maintenance_id")
    private MaintenanceEntity maintenance;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private FormEntity form;

    @ManyToOne
    @JoinColumn(name = "form_field_id")
    private FormFieldEntity formField;

    @Column(name = "value")
    private String value;
}
