package com.trustfund.trustfund.service;

import com.trustfund.trustfund.entity.Due;
import com.trustfund.trustfund.exception.ResourceNotFoundException;
import com.trustfund.trustfund.repository.DueRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DueService {

    @Autowired
    private DueRepository dueRepository;

    public Due createDue(Due due) {
        return dueRepository.save(due);
    }

    public List<Due> getAllDues() {
        return dueRepository.findAll();
    }

    public Due getDueById(Long id) {
        return dueRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Due not found with id: " + id));
    }

    public List<Due> getDuesByUser(Long userId) {
        return dueRepository.findByUserId(userId);
    }

    public List<Due> getDuesBySociety(Long societyId) {
        return dueRepository.findBySocietyId(societyId);
    }

    public List<Due> getDuesByStatus(String status) {
        return dueRepository.findByStatus(status);
    }

    public Due updateDue(Long id, Due dueDetails) {
        Due due = getDueById(id);
        due.setAmount(dueDetails.getAmount());
        due.setDueDate(dueDetails.getDueDate());
        due.setStatus(dueDetails.getStatus());
        due.setPaidDate(dueDetails.getPaidDate());
        due.setUser(dueDetails.getUser());
        due.setSociety(dueDetails.getSociety());
        return dueRepository.save(due);
    }

    public void deleteDue(Long id) {
        Due due = getDueById(id);
        dueRepository.delete(due);
    }
}