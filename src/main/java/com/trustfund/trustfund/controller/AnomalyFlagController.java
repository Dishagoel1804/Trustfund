package com.trustfund.trustfund.controller;

import com.trustfund.trustfund.entity.AnomalyFlag;
import com.trustfund.trustfund.service.AnomalyFlagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/anomaly-flags")
public class AnomalyFlagController {

    @Autowired
    private AnomalyFlagService anomalyFlagService;

    @PostMapping
    public AnomalyFlag createFlag(@RequestBody AnomalyFlag flag) {
        return anomalyFlagService.createFlag(flag);
    }

    @GetMapping
    public List<AnomalyFlag> getAllFlags() {
        return anomalyFlagService.getAllFlags();
    }

    @GetMapping("/{id}")
    public AnomalyFlag getFlagById(@PathVariable Long id) {
        return anomalyFlagService.getFlagById(id);
    }

    @GetMapping("/status/{status}")
    public List<AnomalyFlag> getFlagsByStatus(@PathVariable String status) {
        return anomalyFlagService.getFlagsByStatus(status);
    }

    @GetMapping("/severity/{severity}")
    public List<AnomalyFlag> getFlagsBySeverity(@PathVariable String severity) {
        return anomalyFlagService.getFlagsBySeverity(severity);
    }

    @GetMapping("/entity/{entityType}/{entityId}")
    public List<AnomalyFlag> getFlagsForEntity(@PathVariable String entityType, @PathVariable Long entityId) {
        return anomalyFlagService.getFlagsForEntity(entityType, entityId);
    }

    @PutMapping("/{id}/review")
    public AnomalyFlag reviewFlag(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String finalStatus = body.get("finalStatus");
        String reviewedByEmail = SecurityContextHolder.getContext().getAuthentication().getName();
        return anomalyFlagService.reviewFlag(id, finalStatus, reviewedByEmail);
    }

    @DeleteMapping("/{id}")
    public void deleteFlag(@PathVariable Long id) {
        anomalyFlagService.deleteFlag(id);
    }
}