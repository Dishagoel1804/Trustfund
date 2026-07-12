package com.trustfund.trustfund.service;

import com.trustfund.trustfund.entity.AnomalyFlag;
import com.trustfund.trustfund.exception.ResourceNotFoundException;
import com.trustfund.trustfund.repository.AnomalyFlagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AnomalyFlagService {

    @Autowired
    private AnomalyFlagRepository anomalyFlagRepository;

    public AnomalyFlag createFlag(AnomalyFlag flag) {
        flag.setStatus("OPEN");
        flag.setFlaggedAt(LocalDateTime.now());
        return anomalyFlagRepository.save(flag);
    }

    public List<AnomalyFlag> getAllFlags() {
        return anomalyFlagRepository.findAll();
    }

    public AnomalyFlag getFlagById(Long id) {
        return anomalyFlagRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("AnomalyFlag not found with id: " + id));
    }

    public List<AnomalyFlag> getFlagsByStatus(String status) {
        return anomalyFlagRepository.findByStatus(status);
    }

    public List<AnomalyFlag> getFlagsBySeverity(String severity) {
        return anomalyFlagRepository.findBySeverity(severity);
    }

    public List<AnomalyFlag> getFlagsForEntity(String entityType, Long entityId) {
        return anomalyFlagRepository.findByEntityTypeAndEntityId(entityType, entityId);
    }

    public AnomalyFlag reviewFlag(Long id, String finalStatus, String reviewedByEmail) {
        AnomalyFlag flag = getFlagById(id);
        flag.setStatus(finalStatus); // e.g. "REVIEWED" or "DISMISSED"
        flag.setReviewedByEmail(reviewedByEmail);
        flag.setReviewedAt(LocalDateTime.now());
        return anomalyFlagRepository.save(flag);
    }


}