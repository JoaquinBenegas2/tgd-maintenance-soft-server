package com.tgd.maintenance_soft_server.modules.maintenance.dtos;

import com.tgd.maintenance_soft_server.modules.form.dtos.FormWithoutMaintenanceTypeResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTypeWithFormsResponseDto {

    private Long id;

    private String name;

    private String description;

    private List<FormWithoutMaintenanceTypeResponseDto> forms;
}
