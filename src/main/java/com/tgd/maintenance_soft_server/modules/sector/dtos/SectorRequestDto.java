package com.tgd.maintenance_soft_server.modules.sector.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectorRequestDto {

    private String name;

    private String description;
}
