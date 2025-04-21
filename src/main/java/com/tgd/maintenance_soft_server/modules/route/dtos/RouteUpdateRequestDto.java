package com.tgd.maintenance_soft_server.modules.route.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteUpdateRequestDto {

    private String name;

    private String description;

    @JsonProperty("periodicity_in_days")
    private Integer periodicityInDays;

    @JsonProperty("start_date")
    private LocalDate startDate;
}
