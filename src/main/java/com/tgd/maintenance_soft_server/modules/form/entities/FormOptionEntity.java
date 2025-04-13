package com.tgd.maintenance_soft_server.modules.form.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "FORM_OPTIONS")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormOptionEntity extends BaseEntity {

    @Column(name = "value")
    private String value;

    @ManyToOne
    @JoinColumn(name = "form_field_id")
    private FormFieldEntity formField;
}
