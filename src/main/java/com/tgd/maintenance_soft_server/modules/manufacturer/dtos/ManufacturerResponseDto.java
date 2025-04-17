package com.tgd.maintenance_soft_server.modules.manufacturer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerResponseDto {

    private Long id;

    private String name;

    private String country;

    private Boolean active;
}
