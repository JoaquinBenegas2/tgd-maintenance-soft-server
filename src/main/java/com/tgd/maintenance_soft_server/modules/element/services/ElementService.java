package com.tgd.maintenance_soft_server.modules.element.services;

import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import org.springframework.stereotype.Service;

@Service
public interface ElementService {

    void deleteElementById(Long id, PlantEntity plantEntity);
}
