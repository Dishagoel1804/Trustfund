package com.trustfund.trustfund.controller;

import com.trustfund.trustfund.entity.Due;
import com.trustfund.trustfund.service.AuditLogService;
import com.trustfund.trustfund.service.DueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;


@RestController
@RequestMapping("/api/dues")
public class DueController {

    @Autowired
    private DueService dueService;



    @PostMapping
    public Due createDue(@RequestBody Due due) {
        return dueService.createDue(due);
    }

    @GetMapping
    public List<Due> getAllDues() {
        return dueService.getAllDues();
    }

    @GetMapping("/{id}")
    public Due getDueById(@PathVariable Long id) {
        return dueService.getDueById(id);
    }

    @GetMapping("/user/{userId}")
    public List<Due> getDuesByUser(@PathVariable Long userId) {
        return dueService.getDuesByUser(userId);
    }

    @GetMapping("/society/{societyId}")
    public List<Due> getDuesBySociety(@PathVariable Long societyId) {
        return dueService.getDuesBySociety(societyId);
    }

    @GetMapping("/status/{status}")
    public List<Due> getDuesByStatus(@PathVariable String status) {
        return dueService.getDuesByStatus(status);
    }

    @PutMapping("/{id}")
    public Due updateDue(@PathVariable Long id, @RequestBody Due due) {
        return dueService.updateDue(id, due);
    }

    @DeleteMapping("/{id}")
    public void deleteDue(@PathVariable Long id) {
        dueService.deleteDue(id);
    }
}