package com.example.Lab4_NenashevDA_StaryginVA.jms;

import com.example.Lab4_NenashevDA_StaryginVA.dto.ChangeMessage;
import com.example.Lab4_NenashevDA_StaryginVA.model.AuditLog;
import com.example.Lab4_NenashevDA_StaryginVA.service.AuditService;
import com.example.Lab4_NenashevDA_StaryginVA.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AuditListener {

    private final AuditService auditService;
    private final EmailService emailService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @JmsListener(destination = "changes.topic", containerFactory = "jmsListenerTopicContainerFactory")
    public void onMessage(ChangeMessage message) {
        try {
            AuditLog log = new AuditLog();
            log.setChangeType(message.getChangeType());
            log.setEntityName(message.getEntityName());
            log.setEntityId(message.getEntityId());
            log.setPayload(message.getPayload());
            auditService.save(log);

            String subject = "Новая запись в audit_logs: " + message.getEntityName();
            String body = "Изменение: " + message.getChangeType() + "\n" +
                    "ID: " + message.getEntityId() + "\n" +
                    "Payload: " + message.getPayload() + "\n" +
                    "Timestamp: " + message.getTimestamp();

            emailService.sendSimpleNotification(subject, body);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
