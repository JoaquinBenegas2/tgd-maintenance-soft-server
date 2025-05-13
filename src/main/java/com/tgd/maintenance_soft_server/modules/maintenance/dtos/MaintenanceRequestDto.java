package com.tgd.maintenance_soft_server.modules.maintenance.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgd.maintenance_soft_server.modules.element.entities.ElementEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceAnswerEntity;
import com.tgd.maintenance_soft_server.modules.route.entities.RouteEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceRequestDto {

    @JsonProperty("route_id")
    private Long routeId;

    @JsonProperty("element_id")
    private Long elementId;

    @JsonProperty("form_id")
    private Long formId;

    @JsonProperty("maintenance_date")
    private LocalDate maintenanceDate;

    private List<MaintenanceAnswerRequestDto> answers = new ArrayList<>();

    @JsonProperty("notify_supervisor")
    private Boolean notifySupervisor;
}
