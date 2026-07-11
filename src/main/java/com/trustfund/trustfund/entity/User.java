package com.trustfund.trustfund.entity;

import jakarta.persistence.*;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    private String flatNumber;
    private String role;
    private String password;

    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getFlatNumber() { return flatNumber; }
    public void setFlatNumber(String flatNumber) { this.flatNumber = flatNumber; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public Society getSociety() { return society; }
    public void setSociety(Society society) { this.society = society; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}