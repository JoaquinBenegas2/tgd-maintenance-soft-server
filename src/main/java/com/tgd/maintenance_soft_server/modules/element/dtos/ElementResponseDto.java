package com.tgd.maintenance_soft_server.modules.element.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentResponseDto;
import com.tgd.maintenance_soft_server.modules.element.models.ElementStatus;
import com.tgd.maintenance_soft_server.modules.manufacturer.dtos.ManufacturerResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ElementResponseDto {

    private Long id;

    private String name;

    private String description;

    private ComponentResponseDto component;

    private ManufacturerResponseDto manufacturer;

    @JsonProperty("last_maintenance_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastMaintenanceDate;

    @JsonProperty("last_replacement_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate lastReplacementDate;

    private ElementStatus status;
}
