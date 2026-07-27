package com.notificationservice.provider;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@Slf4j
public class SmtpEmailProvider implements EmailProvider{
    /**
     * Sends an email.
     *
     * @param recipient recipient email address
     * @param subject   email subject
     * @param message   email body
     * @return provider reference/message id
     */
    @Override
    public String sendEmail(String recipient, String subject, String message) {
        String reference = UUID.randomUUID().toString();

        log.info("""
                ================================
                EMAIL SENT
                To      : {}
                Subject : {}
                Body    : {}
                Ref Id  : {}
                ================================
                """,
                recipient,
                subject,
                message,
                reference
        );

        return reference;
    }
}
