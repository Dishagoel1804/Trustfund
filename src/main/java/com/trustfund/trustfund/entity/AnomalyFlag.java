package com.trustfund.trustfund.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class AnomalyFlag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String entityType; // e.g. "Expense", "Due" — same decoupled pattern as AuditLog

    private Long entityId;

    private String reason; // e.g. "Amount 3x higher than average for this category"

    private String severity; // e.g. "LOW", "MEDIUM", "HIGH"

    private String status; // e.g. "OPEN", "REVIEWED", "DISMISSED"

    private LocalDateTime flaggedAt;

    private String reviewedByEmail; // filled in once someone reviews it

    private LocalDateTime reviewedAt;

    // Getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getEntityType() { return entityType; }
    public void setEntityType(String entityType) { this.entityType = entityType; }

    public Long getEntityId() { return entityId; }
    public void setEntityId(Long entityId) { this.entityId = entityId; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getFlaggedAt() { return flaggedAt; }
    public void setFlaggedAt(LocalDateTime flaggedAt) { this.flaggedAt = flaggedAt; }

    public String getReviewedByEmail() { return reviewedByEmail; }
    public void setReviewedByEmail(String reviewedByEmail) { this.reviewedByEmail = reviewedByEmail; }

    public LocalDateTime getReviewedAt() { return reviewedAt; }
    public void setReviewedAt(LocalDateTime reviewedAt) { this.reviewedAt = reviewedAt; }
}