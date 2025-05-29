package com.tgd.maintenance_soft_server.modules.route.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementResponseDto;
import com.tgd.maintenance_soft_server.modules.route.models.RouteStatus;
import com.tgd.maintenance_soft_server.modules.user.dtos.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RouteResponseDto {

    private Long id;

    private String name;

    private String description;

    @JsonProperty("periodicity_in_days")
    private Integer periodicityInDays;

    @JsonProperty("start_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate startDate;

    @JsonProperty("active_from_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate activeFromDate;

    @JsonProperty("assigned_elements")
    private List<ElementResponseDto> assignedElements;

    @JsonProperty("assigned_operators")
    private List<UserResponseDto> assignedOperators;

    private RouteStatus status;
}
