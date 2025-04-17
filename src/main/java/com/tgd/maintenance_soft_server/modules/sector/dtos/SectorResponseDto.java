package com.tgd.maintenance_soft_server.modules.sector.dtos;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SectorResponseDto {

    private Long id;

    private String name;

    private String description;

    private Boolean active;
}
