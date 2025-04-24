package com.tgd.maintenance_soft_server.modules.maintenance.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.tgd.maintenance_soft_server.modules.form.entities.FormEntity;
import com.tgd.maintenance_soft_server.modules.form.entities.FormFieldEntity;
import com.tgd.maintenance_soft_server.modules.maintenance.entities.MaintenanceEntity;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceAnswerRequestDto {

    @JsonProperty("form_field_id")
    private Long formFieldId;

    private String value;
}
