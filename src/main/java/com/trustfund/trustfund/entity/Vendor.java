package com.trustfund.trustfund.entity;

import jakarta.persistence.*;

@Entity
public class Vendor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String contactNumber;
    private String category; // e.g. Plumbing, Electrical, Housekeeping

    @ManyToOne
    @JoinColumn(name = "society_id")
    private Society society;

    // Getters and setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContactNumber() { return contactNumber; }
    public void setContactNumber(String contactNumber) { this.contactNumber = contactNumber; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Society getSociety() { return society; }
    public void setSociety(Society society) { this.society = society; }
}