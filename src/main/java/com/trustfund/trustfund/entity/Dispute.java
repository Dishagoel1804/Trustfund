package com.trustfund.trustfund.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Dispute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String reason;

    private String status; // e.g. "OPEN", "UNDER_REVIEW", "RESOLVED", "REJECTED"

    private String resolutionNotes; // filled in once resolved

    private LocalDateTime createdAt;

    private LocalDateTime resolvedAt; // null until resolved

    @ManyToOne
    @JoinColumn(name = "raised_by_user_id")
    private User raisedBy;

    @ManyToOne
    @JoinColumn(name = "expense_id")
    private Expense expense; // nullable — dispute might be about a due instead

    @ManyToOne
    @JoinColumn(name = "due_id")
    private Due due; // nullable — dispute might be about an expense instead

    // Getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getReason() { return reason; }
    public void setReason(String reason) { this.reason = reason; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getResolutionNotes() { return resolutionNotes; }
    public void setResolutionNotes(String resolutionNotes) { this.resolutionNotes = resolutionNotes; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public LocalDateTime getResolvedAt() { return resolvedAt; }
    public void setResolvedAt(LocalDateTime resolvedAt) { this.resolvedAt = resolvedAt; }

    public User getRaisedBy() { return raisedBy; }
    public void setRaisedBy(User raisedBy) { this.raisedBy = raisedBy; }

    public Expense getExpense() { return expense; }
    public void setExpense(Expense expense) { this.expense = expense; }

    public Due getDue() { return due; }
    public void setDue(Due due) { this.due = due; }
}