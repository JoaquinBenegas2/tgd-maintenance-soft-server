package com.tgd.maintenance_soft_server.modules.element.services;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.component.models.ComponentStatus;
import com.tgd.maintenance_soft_server.modules.element.models.ElementStatus;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementRequestDto;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementResponseDto;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.element.repositories.ElementRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ElementService extends BloService<
        ElementRequestDto,
        ElementResponseDto,
        ElementEntity,
        Long,
        PlantEntity> {
    private final ElementRepository elementRepository;

    @Override
    public ElementRepository getRepository() {
        return elementRepository;
    }

    public List<ElementResponseDto> getAllByStatus(PlantEntity plantEntity, ElementStatus status) {
        return elementRepository
                .findAllByIdentifyingEntityAndStatus(plantEntity, status)
                .stream()
                .map(elementEntity -> modelMapper.map(elementEntity, ElementResponseDto.class))
                .toList();
    }

    @Transactional
    public ElementResponseDto updateElementStatus(Long id, ElementStatus newStatus, PlantEntity plant) {
        ElementEntity element = elementRepository.findByIdAndIdentifyingEntity(id, plant)
                .orElseThrow(() -> new EntityNotFoundException("Element not found"));

        if (newStatus == ElementStatus.ACTIVE && element.getComponent().getStatus() == ComponentStatus.INACTIVE) {
            throw new IllegalStateException("Cannot activate element because its component is inactive");
        }

        element.setStatus(newStatus);
        elementRepository.save(element);
        return modelMapper.map(element, ElementResponseDto.class);
    }
}
