package com.example.Lab4_NenashevDA_StaryginVA.jms;

import com.example.Lab4_NenashevDA_StaryginVA.dto.ChangeMessage;
import com.example.Lab4_NenashevDA_StaryginVA.service.EmailService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class NotificationListener {

    private final EmailService emailService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @JmsListener(destination = "changes.topic", containerFactory = "jmsListenerTopicContainerFactory")
    public void onMessage(ChangeMessage message) {
        try {
            String changeType = message.getChangeType();
            String entity = message.getEntityName();
            String payload = message.getPayload();

            boolean shouldNotify = false;
            String subject = "Событие в системе: " + entity + " " + changeType;
            StringBuilder body = new StringBuilder();
            body.append("Тип: ").append(changeType).append("\n");
            body.append("Сущность: ").append(entity).append("\n");
            body.append("ID: ").append(message.getEntityId()).append("\n");
            body.append("Payload: ").append(payload).append("\n");

            if ("DELETE".equalsIgnoreCase(changeType)) {
                shouldNotify = true;
            } else if ("Task".equals(entity)) {
                JsonNode root = objectMapper.readTree(payload);
                JsonNode completed = root.path("completed");
                if (completed.asBoolean(false)) shouldNotify = true;
            } else if ("User".equals(entity) && "UPDATE".equalsIgnoreCase(changeType)) {
                JsonNode root = objectMapper.readTree(payload);
                JsonNode ageNode = root.path("age");
                if (ageNode.isInt()) {
                    int age = ageNode.asInt();
                    if (age < 18 || age > 75) {
                        shouldNotify = true;
                        body.append("Внимание: возраст за пределами допустимого диапазона: ").append(age).append("\n");
                    }
                }
            }

            if (shouldNotify) {
                emailService.sendSimpleNotification(subject, body.toString());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
