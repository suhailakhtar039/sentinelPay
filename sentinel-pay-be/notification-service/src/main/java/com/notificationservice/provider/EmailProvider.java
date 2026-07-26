package com.notificationservice.provider;

public interface EmailProvider {

    /**
     * Sends an email.
     *
     * @param recipient recipient email address
     * @param subject email subject
     * @param message email body
     * @return provider reference/message id
     */
    String sendEmail(
            String recipient,
            String subject,
            String message
    );
}