package com.tgd.maintenance_soft_server.modules.maintenance.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormFieldResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceAnswerResponseDto {

    private Long id;

    @JsonProperty("form_field")
    private FormFieldResponseDto formField;

    private String value;
}
