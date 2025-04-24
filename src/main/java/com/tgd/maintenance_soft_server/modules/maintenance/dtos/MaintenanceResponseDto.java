package com.tgd.maintenance_soft_server.modules.maintenance.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgd.maintenance_soft_server.modules.element.dtos.ElementResponseDto;
import com.tgd.maintenance_soft_server.modules.form.dtos.FormResponseDto;
import com.tgd.maintenance_soft_server.modules.route.dtos.RouteResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceResponseDto {

    private Long id;

    private RouteResponseDto route;

    private ElementResponseDto element;

    private FormResponseDto form;

    @JsonProperty("maintenance_date")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate maintenanceDate;

    private List<MaintenanceAnswerResponseDto> answers = new ArrayList<>();
}
