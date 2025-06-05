package com.tgd.maintenance_soft_server.modules.route.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DelayedRouteItemDto {
    private String name;
    private String lastExpectedDate;
}
