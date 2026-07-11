package com.trustfund.trustfund.controller;

import com.trustfund.trustfund.entity.AuditLog;
import com.trustfund.trustfund.service.AuditLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/audit-logs")
public class AuditLogController {

    @Autowired
    private AuditLogService auditLogService;

    @GetMapping
    public List<AuditLog> getAllLogs() {
        return auditLogService.getAllLogs();
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public List<AuditLog> getLogsForEntity(@PathVariable String entityType, @PathVariable Long entityId) {
        return auditLogService.getLogsForEntity(entityType, entityId);
    }

    @GetMapping("/user/{email}")
    public List<AuditLog> getLogsByUser(@PathVariable String email) {
        return auditLogService.getLogsByUser(email);
    }
}