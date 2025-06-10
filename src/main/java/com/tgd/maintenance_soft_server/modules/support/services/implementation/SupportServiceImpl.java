package com.tgd.maintenance_soft_server.modules.support.services.implementation;

import com.tgd.maintenance_soft_server.modules.email.dtos.EmailRequestDto;
import com.tgd.maintenance_soft_server.modules.email.models.EmailType;
import com.tgd.maintenance_soft_server.modules.email.services.EmailService;
import com.tgd.maintenance_soft_server.modules.plant.entities.PlantEntity;
import com.tgd.maintenance_soft_server.modules.email.dtos.AttachmentDto;
import com.tgd.maintenance_soft_server.modules.support.dtos.ReportAProblemDto;
import com.tgd.maintenance_soft_server.modules.support.services.SupportService;
import com.tgd.maintenance_soft_server.modules.user.entities.UserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SupportServiceImpl implements SupportService {
    private final EmailService emailService;

    @Value("${support.dev-emails}")
    private String developmentEmails;

    @Override
    public void reportAProblem(
            PlantEntity plantEntity,
            ReportAProblemDto reportAProblemDto,
            List<MultipartFile> images
    ) throws IOException {
        EmailRequestDto emailRequest = new EmailRequestDto();
        emailRequest.setTo(List.of(developmentEmails.split(",")));
        emailRequest.setSubject("🛠️ Report a problem: " + reportAProblemDto.getTitle());
        emailRequest.setEmailType(EmailType.REPORT_A_PROBLEM);
        emailRequest.setVariables(Map.of(
                "supervisorName", "Support Team",
                "title", reportAProblemDto.getTitle(),
                "description", reportAProblemDto.getDescription(),
                "plantName", plantEntity.getName()
        ));

        if (images != null && !images.isEmpty()) {
            List<AttachmentDto> attachments = new ArrayList<>();
            for (MultipartFile file : images) {
                attachments.add(new AttachmentDto(
                        file.getOriginalFilename(),
                        file.getContentType(),
                        file.getBytes()
                ));
            }

            emailRequest.setAttachments(attachments);
        }

        emailService.sendTemplatedEmail(emailRequest);
    }
}
