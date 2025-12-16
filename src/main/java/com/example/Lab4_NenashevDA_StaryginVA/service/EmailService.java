package com.example.Lab4_NenashevDA_StaryginVA.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EmailService {

    private final JavaMailSender mailSender;

    @Value("${app.notification.emails:}")
    private String notificationEmails;

    public void sendSimpleNotification(String subject, String text) {
        if (notificationEmails == null || notificationEmails.isBlank()) return;

        String[] recipients = notificationEmails.split(",");
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("no-reply@example.com");
        message.setTo(recipients);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
