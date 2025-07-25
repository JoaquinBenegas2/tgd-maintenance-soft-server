package com.tgd.maintenance_soft_server.modules.form.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormRequestDto {

    private String name;

    private String description;

    private Long maintenanceTypeId;

    private List<FormFieldRequestDto> fields;
}
