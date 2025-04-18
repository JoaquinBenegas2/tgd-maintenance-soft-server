package com.tgd.maintenance_soft_server.modules.asset.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgd.maintenance_soft_server.modules.asset.models.AssetStatus;
import com.tgd.maintenance_soft_server.modules.manufacturer.entities.ManufacturerEntity;
import com.tgd.maintenance_soft_server.modules.sector.entities.SectorEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssetRequestDto {

    private String name;

    private String description;

    @JsonProperty("sector_id")
    private Long sectorId;

    @JsonProperty("manufacturer_id")
    private Long manufacturerId;

    private String model;

    @JsonProperty("serial_number")
    private String serialNumber;

    private AssetStatus status;

    @JsonProperty("installation_date")
    private LocalDate installationDate;
}
