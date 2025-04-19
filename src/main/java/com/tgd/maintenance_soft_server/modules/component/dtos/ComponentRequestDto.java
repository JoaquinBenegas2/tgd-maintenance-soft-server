package com.tgd.maintenance_soft_server.modules.component.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComponentRequestDto {

    private String name;

    private String description;

    @JsonProperty("asset_id")
    private Long assetId;

    @JsonProperty("manufacturer_id")
    private Long manufacturerId;

    private String model;

    @JsonProperty("serial_number")
    private String serialNumber;
}
