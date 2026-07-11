package com.trustfund.trustfund.service;

import com.trustfund.trustfund.entity.Dispute;
import com.trustfund.trustfund.exception.ResourceNotFoundException;
import com.trustfund.trustfund.repository.DisputeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.security.core.context.SecurityContextHolder;
import com.trustfund.trustfund.service.AuditLogService;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class DisputeService {

    @Autowired
    private DisputeRepository disputeRepository;

    @Autowired
    private AuditLogService auditLogService;

    public Dispute createDispute(Dispute dispute) {
        dispute.setStatus("OPEN");
        dispute.setCreatedAt(LocalDateTime.now());
        return disputeRepository.save(dispute);
    }

    public List<Dispute> getAllDisputes() {
        return disputeRepository.findAll();
    }

    public Dispute getDisputeById(Long id) {
        return disputeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Dispute not found with id: " + id));
    }

    public List<Dispute> getDisputesByUser(Long userId) {
        return disputeRepository.findByRaisedById(userId);
    }

    public List<Dispute> getDisputesByStatus(String status) {
        return disputeRepository.findByStatus(status);
    }

    public Dispute resolveDispute(Long id, String resolutionNotes, String finalStatus) {
        Dispute dispute = getDisputeById(id);
        dispute.setResolutionNotes(resolutionNotes);
        dispute.setStatus(finalStatus);
        dispute.setResolvedAt(LocalDateTime.now());
        Dispute saved = disputeRepository.save(dispute);

        String performedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogService.logAction("RESOLVE_DISPUTE", "Dispute", saved.getId(), performedBy,
                "Status set to " + finalStatus);

        return saved;
    }

    public void deleteDispute(Long id) {
        Dispute dispute = getDisputeById(id);
        disputeRepository.delete(dispute);
    }
}