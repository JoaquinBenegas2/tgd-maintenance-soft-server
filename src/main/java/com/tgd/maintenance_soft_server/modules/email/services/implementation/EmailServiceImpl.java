package com.tgd.maintenance_soft_server.modules.email.services.implementation;

import com.tgd.maintenance_soft_server.modules.email.dtos.EmailRequestDto;
import com.tgd.maintenance_soft_server.modules.email.services.EmailService;
import com.tgd.maintenance_soft_server.modules.email.services.EmailTemplateProcessor;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.w3c.tidy.Tidy;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;
    private final Tidy tidy;
    private final EmailTemplateProcessor templateProcessor;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    @Override
    public void sendTemplatedEmail(EmailRequestDto dto) {
        String templateName = dto.getEmailType().getTemplateName();
        String processedBody = templateProcessor.processTemplate(templateName, dto.getVariables());
        sendEmail(dto.getTo(), dto.getSubject(), processedBody);
    }

    /**
     * Sends an email using the JavaMailSender.
     *
     * @param to      recipient's email address
     * @param subject subject of the email
     * @param body    body of the email
     */
    @SneakyThrows
    @Override
    public void sendEmail(List<String> to, String subject, String body) {
        if (!isValidHtml(body)) {
            throw new MessagingException("Invalid HTML body");
        }

        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "utf-8");
        helper.setTo(to.toArray(new String[0]));
        helper.setSubject(subject);
        helper.setText(body, true);
        helper.setFrom(from);

        mailSender.send(message);
    }

    /**
     * This method validates if the html sent is valid or not.
     *
     * @param html which represents the HTML string
     * @return if it's valid or not
     */
    private boolean isValidHtml(String html) {
        try (StringReader input = new StringReader(html); StringWriter output = new StringWriter()) {
            tidy.parse(input, output);
            return tidy.getParseErrors() <= 0;
        } catch (Exception e) {
            return false;
        }
    }
}
