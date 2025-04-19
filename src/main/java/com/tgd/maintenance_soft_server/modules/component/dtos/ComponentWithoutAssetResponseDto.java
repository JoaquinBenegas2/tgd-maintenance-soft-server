package com.tgd.maintenance_soft_server.modules.component.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgd.maintenance_soft_server.modules.component.models.ComponentStatus;
import com.tgd.maintenance_soft_server.modules.manufacturer.dtos.ManufacturerResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentWithoutAssetResponseDto {

    private Long id;

    private String name;

    private String description;

    private ManufacturerResponseDto manufacturer;

    private String model;

    @JsonProperty("serial_number")
    private String serialNumber;

    private ComponentStatus status;
}
