package com.tgd.maintenance_soft_server.modules.maintenance.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceTypeRequestDto {

    private String name;

    private String description;
}
