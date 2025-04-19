package com.tgd.maintenance_soft_server.modules.element.services.implementation;

import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.element.repositories.ElementRepository;
import com.tgd.maintenance_soft_server.modules.element.services.ElementService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ElementServiceImpl implements ElementService {

    private final ElementRepository elementRepository;

    @Override
    public void deleteElementById(Long id, PlantEntity plantEntity) {
        ElementEntity existingAsset = elementRepository.findByIdAndIdentifyingEntity(id, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found or not associated with the plant"));

        elementRepository.delete(existingAsset);
    }
}
