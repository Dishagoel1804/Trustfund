package com.trustfund.trustfund.controller;

import com.trustfund.trustfund.entity.Dispute;
import com.trustfund.trustfund.service.DisputeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/disputes")
public class DisputeController {

    @Autowired
    private DisputeService disputeService;

    @PostMapping
    public Dispute createDispute(@RequestBody Dispute dispute) {
        return disputeService.createDispute(dispute);
    }

    @GetMapping
    public List<Dispute> getAllDisputes() {
        return disputeService.getAllDisputes();
    }

    @GetMapping("/{id}")
    public Dispute getDisputeById(@PathVariable Long id) {
        return disputeService.getDisputeById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Dispute> getDisputesByUser(@PathVariable Long userId) {
        return disputeService.getDisputesByUser(userId);
    }

    @GetMapping("/status/{status}")
    public List<Dispute> getDisputesByStatus(@PathVariable String status) {
        return disputeService.getDisputesByStatus(status);
    }

    @PutMapping("/{id}/resolve")
    public Dispute resolveDispute(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String resolutionNotes = body.get("resolutionNotes");
        String finalStatus = body.get("finalStatus");
        return disputeService.resolveDispute(id, resolutionNotes, finalStatus);
    }

    @DeleteMapping("/{id}")
    public void deleteDispute(@PathVariable Long id) {
        disputeService.deleteDispute(id);
    }
}