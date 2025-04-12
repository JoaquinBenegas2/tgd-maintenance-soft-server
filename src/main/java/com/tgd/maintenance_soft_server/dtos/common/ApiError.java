package com.tgd.maintenance_soft_server.dtos.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiError {

    private Integer status;
    private String error;
    private String message;
    private String timestamp;
}
