package com.tgd.maintenance_soft_server.modules.email.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AttachmentDto {

    private String fileName;

    private String contentType;

    private byte[] content;
}
