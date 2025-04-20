package com.tgd.maintenance_soft_server.modules.form.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceTypeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormResponseDto {

    private Long id;

    private String name;

    private String description;

    @JsonProperty("maintenance_type")
    private MaintenanceTypeResponseDto maintenanceType;

    private List<FormFieldResponseDto> fields;
}
