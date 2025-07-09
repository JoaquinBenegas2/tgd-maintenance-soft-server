package com.tgd.maintenance_soft_server.modules.form.dtos;

import com.tgd.maintenance_soft_server.modules.form.models.FormFieldType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormFieldRequestDto {
    private String name;

    private FormFieldType type;

    private Boolean required;

    private Integer order;

    private List<String> options;
}
