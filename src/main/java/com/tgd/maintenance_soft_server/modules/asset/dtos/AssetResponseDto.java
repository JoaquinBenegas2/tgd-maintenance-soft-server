package com.tgd.maintenance_soft_server.modules.asset.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgd.maintenance_soft_server.modules.asset.models.AssetStatus;
import com.tgd.maintenance_soft_server.modules.component.dtos.ComponentWithoutAssetResponseDto;
import com.tgd.maintenance_soft_server.modules.manufacturer.dtos.ManufacturerResponseDto;
import com.tgd.maintenance_soft_server.modules.sector.dtos.SectorResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetResponseDto {

    private Long id;

    private String name;

    private String description;

    private SectorResponseDto sector;

    private ManufacturerResponseDto manufacturer;

    private String model;

    @JsonProperty("serial_number")
    private String serialNumber;

    private AssetStatus status;

    @JsonProperty("installation_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate installationDate;

    private List<ComponentWithoutAssetResponseDto> components;
}
