package com.tgd.maintenance_soft_server.modules.form.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import com.tgd.maintenance_soft_server.interfaces.BaseIdentifyingEntity;
import com.tgd.maintenance_soft_server.modules.form.models.FormFieldType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "FORM_FIELDS")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FormFieldEntity extends BaseIdentifyingEntity {

    @Column(name = "name")
    private  String name;

    @Enumerated(EnumType.STRING)
    private FormFieldType type;

    @OneToMany(mappedBy = "formField", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FormOptionEntity> options = new ArrayList<>();

    @Column(name = "is_required")
    private Boolean required;

    @Column(name = "field_order")
    private Integer order;

    @ManyToOne
    @JoinColumn(name = "form_id")
    private FormEntity form;
}
