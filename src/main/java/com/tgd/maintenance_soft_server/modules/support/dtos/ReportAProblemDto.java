package com.tgd.maintenance_soft_server.modules.support.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReportAProblemDto {

    private String title;

    private String description;
}
