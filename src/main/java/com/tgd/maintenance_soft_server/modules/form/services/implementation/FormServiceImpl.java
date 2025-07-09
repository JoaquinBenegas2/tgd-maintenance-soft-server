package com.tgd.maintenance_soft_server.modules.form.services.implementation;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormFieldResponseDto;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormOptionResponseDto;
import com.tgd.maintenance_soft_server.modules.form.entities.FormFieldEntity;
import com.tgd.maintenance_soft_server.modules.form.entities.FormOptionEntity;
import com.tgd.maintenance_soft_server.modules.form.models.FormFieldType;
import com.tgd.maintenance_soft_server.modules.form.services.FormService;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceTypeEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.repositories.MaintenanceTypeRepository;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormRequestDto;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormResponseDto;
import com.tgd.maintenance_soft_server.modules.form.entities.FormEntity;
import com.tgd.maintenance_soft_server.modules.form.repositories.FormRepository;
import com.tgd.maintenance_soft_server.modules.route.dtos.RouteResponseDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FormServiceImpl extends BloService<
        FormRequestDto,
        FormResponseDto,
        FormEntity,
        Long,
        PlantEntity>
        implements FormService {

    private final FormRepository formRepository;
    private final MaintenanceTypeRepository maintenanceTypeRepository;

    @Override
    public FormRepository getRepository() {
        return formRepository;
    }


    @Override
    public FormResponseDto create(PlantEntity plantEntity, FormRequestDto formRequestDto) {
        FormEntity form = new FormEntity();
        form.setName(formRequestDto.getName());
        form.setDescription(formRequestDto.getDescription());

        if (formRequestDto.getMaintenanceTypeId() != null) {
            MaintenanceTypeEntity mt = maintenanceTypeRepository.findById(formRequestDto.getMaintenanceTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("MaintenanceType not found"));
            form.setMaintenanceType(mt);
        }

        form.setIdentifyingEntity(plantEntity);

        List<FormFieldEntity> fields = formRequestDto.getFields().stream()
                .map(req -> {
                    FormFieldEntity field = new FormFieldEntity();
                    field.setName(req.getName());
                    field.setType(req.getType());
                    field.setRequired(req.getRequired());
                    field.setOrder(req.getOrder());
                    field.setForm(form);
                    field.setIdentifyingEntity(plantEntity);

                    if (req.getType() == FormFieldType.SELECT) {
                        List<FormOptionEntity> options = req.getOptions().stream()
                                .map(value -> {
                                    FormOptionEntity formOption = new FormOptionEntity();
                                    formOption.setValue(value);
                                    formOption.setFormField(field);
                                    formOption.setIdentifyingEntity(plantEntity);
                                    return formOption;
                                })
                                .collect(Collectors.toList());
                        field.setOptions(options);
                    }
                    return field;
                })
                .collect(Collectors.toList());

        form.setFields(fields);
        FormEntity saved = formRepository.save(form);

        FormResponseDto formResponseDto = new FormResponseDto();
        formResponseDto.setId(saved.getId());
        formResponseDto.setName(saved.getName());
        formResponseDto.setDescription(saved.getDescription());
        formResponseDto.setFields(saved.getFields().stream().map(field -> {
            FormFieldResponseDto formFieldResponseDto = new FormFieldResponseDto();
            formFieldResponseDto.setId(field.getId());
            formFieldResponseDto.setName(field.getName());
            formFieldResponseDto.setType(field.getType());
            formFieldResponseDto.setRequired(field.getRequired());
            formFieldResponseDto.setOrder(field.getOrder());
            formFieldResponseDto.setOptions(
                    field.getOptions().stream()
                            .map(FormOptionResponseDto::fromEntity)
                            .collect(Collectors.toList())
            );
            return formFieldResponseDto;
        }).collect(Collectors.toList()));

        return formResponseDto;
    }
}
