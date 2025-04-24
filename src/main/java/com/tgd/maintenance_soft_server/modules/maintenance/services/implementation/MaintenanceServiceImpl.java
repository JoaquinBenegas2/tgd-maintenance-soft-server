package com.tgd.maintenance_soft_server.modules.maintenance.services.implementation;

import com.tgd.maintenance_soft_server.lib.blo_service.services.BloService;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementResponseDto;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.element.repositories.ElementRepository;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormResponseDto;
import com.tgd.maintenance_soft_server.modules.form.entities.FormEntity;
import com.tgd.maintenance_soft_server.modules.form.entities.FormFieldEntity;
import com.tgd.maintenance_soft_server.modules.form.repositories.FormFieldRepository;
import com.tgd.maintenance_soft_server.modules.form.repositories.FormRepository;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceAnswerResponseDto;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceRequestDto;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceResponseDto;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceAnswerEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.repositories.MaintenanceRepository;
import com.tgd.maintenance_soft_server.modules.maintenance.services.MaintenanceService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.route.dtos.RouteResponseDto;
import com.tgd.maintenance_soft_server.modules.route.entities.RouteEntity;
import com.tgd.maintenance_soft_server.modules.route.repositories.RouteRepository;
import com.tgd.maintenance_soft_server.modules.route.services.RouteService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MaintenanceServiceImpl
        extends BloService<MaintenanceRequestDto, MaintenanceResponseDto, MaintenanceEntity, Long, PlantEntity>
        implements MaintenanceService {

    private final MaintenanceRepository maintenanceRepository;
    private final ModelMapper modelMapper;
    private final RouteRepository routeRepository;
    private final ElementRepository elementRepository;
    private final FormRepository formRepository;
    private final FormFieldRepository formFieldRepository;
    private final RouteService routeService;

    @Override
    public MaintenanceRepository getRepository() {
        return maintenanceRepository;
    }

    @Override
    protected MaintenanceResponseDto toDto(MaintenanceEntity entity) {
        MaintenanceResponseDto responseDto = mapEntityToDto(entity);
        if (!entity.getAnswers().isEmpty()) {
            responseDto.setForm(modelMapper.map(entity.getAnswers().get(0).getForm(), FormResponseDto.class));
        }

        return responseDto;
    }

    @Override
    @Transactional
    public MaintenanceResponseDto createMaintenance(PlantEntity plantEntity, MaintenanceRequestDto maintenanceRequestDto) {
        RouteEntity routeEntity = routeRepository
                .findByIdAndIdentifyingEntity(maintenanceRequestDto.getRouteId(), plantEntity)
                .orElseThrow(() -> new RuntimeException("Route not found or not associated with plant"));

        ElementEntity elementEntity = elementRepository
                .findByIdAndIdentifyingEntity(maintenanceRequestDto.getElementId(), plantEntity)
                .orElseThrow(() -> new RuntimeException("Element not found or not associated with plant"));

        FormEntity formEntity = formRepository
                .findByIdAndIdentifyingEntity(maintenanceRequestDto.getFormId(), plantEntity)
                .orElseThrow(() -> new RuntimeException("Form not found or not associated with plant"));

        MaintenanceEntity maintenanceEntity = new MaintenanceEntity();
        maintenanceEntity.setName("");
        maintenanceEntity.setDescription("");
        maintenanceEntity.setRoute(routeEntity);
        maintenanceEntity.setElement(elementEntity);
        maintenanceEntity.setIdentifyingEntity(plantEntity);
        maintenanceEntity.setMaintenanceDate(maintenanceRequestDto.getMaintenanceDate());

        maintenanceRequestDto.getAnswers().forEach(answer -> {
            FormFieldEntity formFieldEntity = formFieldRepository
                    .findByIdAndIdentifyingEntity(answer.getFormFieldId(), plantEntity)
                    .orElseThrow(() -> new RuntimeException("Form field not found or not associated with form"));

            MaintenanceAnswerEntity maintenanceAnswerEntity = new MaintenanceAnswerEntity();
            maintenanceAnswerEntity.setMaintenance(maintenanceEntity);
            maintenanceAnswerEntity.setForm(formEntity);
            maintenanceAnswerEntity.setFormField(formFieldEntity);
            maintenanceAnswerEntity.setValue(answer.getValue());

            maintenanceEntity.getAnswers().add(maintenanceAnswerEntity);
        });

        MaintenanceEntity savedMaintenance = maintenanceRepository.save(maintenanceEntity);

        MaintenanceResponseDto responseDto = mapEntityToDto(savedMaintenance);
        responseDto.setForm(modelMapper.map(formEntity, FormResponseDto.class));

        return responseDto;
    }

    private MaintenanceResponseDto mapEntityToDto(MaintenanceEntity maintenanceEntity) {
        MaintenanceResponseDto maintenanceResponseDto = new MaintenanceResponseDto();
        maintenanceResponseDto.setId(maintenanceEntity.getId());
        maintenanceResponseDto.setRoute(routeService.mapToResponseDto(maintenanceEntity.getRoute()));
        maintenanceResponseDto.setElement(modelMapper.map(maintenanceEntity.getElement(), ElementResponseDto.class));
        maintenanceResponseDto.setMaintenanceDate(maintenanceEntity.getMaintenanceDate());
        maintenanceResponseDto.setAnswers(maintenanceEntity.getAnswers().stream()
                .map(answer -> modelMapper.map(answer, MaintenanceAnswerResponseDto.class))
                .toList());
        return maintenanceResponseDto;
    }
}
