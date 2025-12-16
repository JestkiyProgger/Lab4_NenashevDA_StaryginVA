package com.example.Lab4_NenashevDA_StaryginVA.repository;

import com.example.Lab4_NenashevDA_StaryginVA.model.AuditLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuditLogRepository extends JpaRepository<AuditLog, Long> {}
