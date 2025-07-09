package com.tgd.maintenance_soft_server.modules.form.dtos;

import com.tgd.maintenance_soft_server.modules.form.entities.FormOptionEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FormOptionResponseDto {

    private Long id;

    private String value;

    public static FormOptionResponseDto fromEntity(FormOptionEntity entity) {
        FormOptionResponseDto dto = new FormOptionResponseDto();
        dto.setId(entity.getId());
        dto.setValue(entity.getValue());
        return dto;
    }

}
