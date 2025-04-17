package com.tgd.maintenance_soft_server.interfaces;

import com.tgd.maintenance_soft_server.lib.blo_service.interfaces.IdentifyingEntity;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class BaseIdentifyingEntity extends BaseEntity implements IdentifyingEntity<PlantEntity> {

    @ManyToOne
    private PlantEntity identifyingEntity;

    @Override
    public void setIdentifyingEntity(PlantEntity plant) {
        this.identifyingEntity = plant;
    }

    @Override
    public PlantEntity getIdentifyingEntity() {
        return this.identifyingEntity;
    }
}
