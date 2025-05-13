package com.tgd.maintenance_soft_server.modules.email.services;

import com.tgd.maintenance_soft_server.modules.email.dtos.EmailRequestDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface EmailService {

    void sendTemplatedEmail(EmailRequestDto dto);

    void sendEmail(List<String> to, String subject, String message);
}
