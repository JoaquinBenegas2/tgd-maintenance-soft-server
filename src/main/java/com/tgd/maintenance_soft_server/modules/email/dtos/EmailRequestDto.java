package com.tgd.maintenance_soft_server.modules.email.dtos;

import com.tgd.maintenance_soft_server.modules.email.models.EmailType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

/**
 * Data Transfer Object (DTO) representing the creation of an email.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailRequestDto {

    private List<String> to;

    private String subject;

    private EmailType emailType;

    private Map<String, Object> variables;

    private List<AttachmentDto> attachments;
}
