package com.tgd.maintenance_soft_server.modules.company.entities;

import com.tgd.maintenance_soft_server.interfaces.BaseEntity;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COMPANIES")

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CompanyEntity extends BaseEntity {

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "company")
    private List<PlantEntity> plants = new ArrayList<>();

    @OneToMany(mappedBy = "company")
    private List<UserEntity> assignedUsers = new ArrayList<>();
}
