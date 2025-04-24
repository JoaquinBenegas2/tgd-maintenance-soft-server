package com.tgd.maintenance_soft_server.modules.maintenance.services;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormResponseDto;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormWithoutMaintenanceTypeResponseDto;
import com.tgd.maintenance_soft_server.modules.form.entities.FormEntity;
import com.tgd.maintenance_soft_server.modules.form.repositories.FormRepository;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceTypeRequestDto;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceTypeResponseDto;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceTypeWithFormsResponseDto;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceTypeEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.repositories.MaintenanceTypeRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MaintenanceTypeService extends BloService<
        MaintenanceTypeRequestDto,
        MaintenanceTypeResponseDto,
        MaintenanceTypeEntity,
        Long,
        PlantEntity> {
    private final MaintenanceTypeRepository maintenanceTypeRepository;
    private final ModelMapper modelMapper;
    private final FormRepository formRepository;

    public List<MaintenanceTypeWithFormsResponseDto> getMaintenanceTypeWithFormsList(PlantEntity plant) {
        List<MaintenanceTypeEntity> types = maintenanceTypeRepository.findAllByIdentifyingEntity(plant);

        if (types.isEmpty()) {
            return Collections.emptyList();
        }

        List<Long> typeIds = types.stream()
                .map(MaintenanceTypeEntity::getId)
                .toList();

        List<FormEntity> allForms = formRepository
                .findAllByMaintenanceTypeIdInAndIdentifyingEntity(typeIds, plant);

        Map<Long, List<FormWithoutMaintenanceTypeResponseDto>> formsByType =
                allForms.stream()
                        .collect(Collectors.groupingBy(
                                formEntity -> formEntity.getMaintenanceType().getId(),
                                Collectors.mapping(
                                        formEntity -> modelMapper.map(formEntity, FormWithoutMaintenanceTypeResponseDto.class),
                                        Collectors.toList()
                                )
                        ));

        return types.stream()
                .map(type -> {
                    MaintenanceTypeWithFormsResponseDto dto = modelMapper
                            .map(type, MaintenanceTypeWithFormsResponseDto.class);

                    dto.setForms(formsByType.getOrDefault(type.getId(), List.of()));
                    return dto;
                })
                .toList();
    }

    @Override
    public MaintenanceTypeRepository getRepository() {
        return maintenanceTypeRepository;
    }
}
