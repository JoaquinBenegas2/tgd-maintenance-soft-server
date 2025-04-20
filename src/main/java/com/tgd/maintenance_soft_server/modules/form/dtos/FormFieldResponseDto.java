package com.tgd.maintenance_soft_server.modules.form.dtos;

import com.tgd.maintenance_soft_server.modules.form.models.FormFieldType;
import com.tgd.maintenance_soft_server.modules.maintenance.dtos.MaintenanceTypeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormFieldResponseDto {

    private Long id;

    private String name;

    private FormFieldType type;

    private Boolean required;

    private Integer order;

    private List<FormOptionResponseDto> options;
}
