package com.tgd.maintenance_soft_server.modules.company.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "COMPANIES")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity extends BaseEntity {

    @Column(name = "name")
    private String name;
}
