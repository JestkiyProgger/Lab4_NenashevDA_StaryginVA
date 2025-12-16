package com.example.Lab4_NenashevDA_StaryginVA.service;

import com.example.Lab4_NenashevDA_StaryginVA.model.AuditLog;
import com.example.Lab4_NenashevDA_StaryginVA.repository.AuditLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class AuditService {

    private final AuditLogRepository auditLogRepository;

    public AuditLog save(AuditLog auditLog) {
        return auditLogRepository.save(auditLog);
    }
}
