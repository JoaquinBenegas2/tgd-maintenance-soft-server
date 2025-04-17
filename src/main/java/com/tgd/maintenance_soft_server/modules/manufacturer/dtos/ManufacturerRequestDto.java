package com.tgd.maintenance_soft_server.modules.manufacturer.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ManufacturerRequestDto {

    private String name;

    private String country;
}
