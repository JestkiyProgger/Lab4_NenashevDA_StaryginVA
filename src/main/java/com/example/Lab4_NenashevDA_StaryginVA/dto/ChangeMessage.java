package com.example.Lab4_NenashevDA_StaryginVA.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
public class ChangeMessage implements Serializable {
    private String changeType;
    private String entityName;
    private Long entityId;
    private String payload;
    private OffsetDateTime timestamp = OffsetDateTime.now();
}
