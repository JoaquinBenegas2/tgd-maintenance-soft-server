package com.tgd.maintenance_soft_server.modules.component.services.implementation;

import com.tgd.maintenance_soft_server.lib.blo_service.repositories.BloRepository;
import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.asset.models.AssetStatus;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentRequestDto;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import com.tgd.maintenance_soft_server.modules.component.entities.ComponentEntity;
import com.tgd.maintenance_soft_server.modules.component.models.ComponentStatus;
import com.tgd.maintenance_soft_server.modules.component.repositories.ComponentRepository;
import com.tgd.maintenance_soft_server.modules.component.services.ComponentService;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.element.models.ElementStatus;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ComponentServiceImpl
        extends BloService<ComponentRequestDto, ComponentResponseDto, ComponentEntity, Long, PlantEntity>
        implements ComponentService {

    private final ComponentRepository componentRepository;
    private final ModelMapper modelMapper;

    @Override
    public BloRepository<ComponentEntity, Long, PlantEntity> getRepository() {
        return componentRepository;
    }

    @Override
    public List<ComponentResponseDto> getAllByStatus(PlantEntity plantEntity, ComponentStatus status) {
        return componentRepository.findAllByIdentifyingEntityAndStatus(plantEntity, status)
                .stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteComponentById(Long id, PlantEntity plantEntity) {
        ComponentEntity existingAsset = componentRepository.findByIdAndIdentifyingEntity(id, plantEntity)
                .orElseThrow(() -> new EntityNotFoundException("Asset not found or not associated with the plant"));

        componentRepository.delete(existingAsset);
    }

    @Override
    @Transactional
    public ComponentResponseDto updateComponentStatus(Long id, ComponentStatus newStatus, PlantEntity plant) {
        ComponentEntity component = componentRepository.findByIdAndIdentifyingEntity(id, plant)
                .orElseThrow(() -> new EntityNotFoundException("Component not found"));

        if (newStatus == ComponentStatus.ACTIVE && component.getAsset().getStatus() == AssetStatus.INACTIVE) {
            throw new IllegalStateException("Cannot activate component because its asset is inactive");
        }

        component.setStatus(newStatus);

        for (ElementEntity element : component.getElements()) {
            element.setStatus(ElementStatus.valueOf(newStatus.name()));
        }

        componentRepository.save(component);
        return modelMapper.map(component, ComponentResponseDto.class);
    }
}
