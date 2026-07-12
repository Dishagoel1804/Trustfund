package com.trustfund.trustfund.entity;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
public class Due {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double amount;

    private LocalDate dueDate;

    private String status; // e.g. "PENDING", "PAID", "OVERDUE"

    private LocalDate paidDate; // null until paid

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;

    // Getters and setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate dueDate) { this.dueDate = dueDate; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDate getPaidDate() { return paidDate; }
    public void setPaidDate(LocalDate paidDate) { this.paidDate = paidDate; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Society getSociety() { return society; }
    public void setSociety(Society society) { this.society = society; }

    private Boolean deleted = false;

    public Boolean getDeleted() { return deleted; }
    public void setDeleted(Boolean deleted) { this.deleted = deleted; }
}