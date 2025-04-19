package com.tgd.maintenance_soft_server.modules.component.services.implementation;

import com.tgd.maintenance_soft_server.modules.component.services.ComponentService;
import com.tgd.maintenance_soft_server.modules.component.entities.ComponentEntity;
import com.tgd.maintenance_soft_server.modules.component.repositories.ComponentRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComponentServiceImpl implements ComponentService {

    private final ComponentRepository componentRepository;

    @Override
    public void deleteComponentById(Long id, PlantEntity plantEntity) {
        ComponentEntity existingAsset = componentRepository.findByIdAndIdentifyingEntity(id, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found or not associated with the plant"));

        componentRepository.delete(existingAsset);
    }
}
