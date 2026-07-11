package com.trustfund.trustfund.controller;

import com.trustfund.trustfund.entity.Society;
import com.trustfund.trustfund.service.SocietyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/societies")
public class SocietyController {

    @Autowired
    private SocietyService societyService;

    @PostMapping
    public Society createSociety(@RequestBody Society society) {
        return societyService.createSociety(society);
    }

    @GetMapping
    public List<Society> getAllSocieties() {
        return societyService.getAllSocieties();
    }

    @GetMapping("/{id}")
    public Society getSocietyById(@PathVariable Long id) {
        return societyService.getSocietyById(id);
    }

    @PutMapping("/{id}")
    public Society updateSociety(@PathVariable Long id, @RequestBody Society society) {
        return societyService.updateSociety(id, society);
    }

    @DeleteMapping("/{id}")
    public void deleteSociety(@PathVariable Long id) {
        societyService.deleteSociety(id);
    }
}