package com.tgd.maintenance_soft_server.modules.maintenance.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MaintenanceUpdateRequestDto {
    private List<MaintenanceAnswerRequestDto> answers = new ArrayList<>();
}
