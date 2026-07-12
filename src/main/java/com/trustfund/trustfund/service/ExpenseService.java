package com.trustfund.trustfund.service;

import com.trustfund.trustfund.entity.Expense;
import com.trustfund.trustfund.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trustfund.trustfund.exception.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import com.trustfund.trustfund.entity.AnomalyFlag;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private AnomalyFlagService anomalyFlagService;


    public Expense createExpense(Expense expense) {
        Expense saved = expenseRepository.save(expense);

        checkForAnomaly(saved);

        String performedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogService.logAction("CREATE_EXPENSE", "Expense", saved.getId(), performedBy,
                "Created expense: " + saved.getDescription() + ", amount: " + saved.getAmount());

        return saved;
    }
    public List<Expense> getAllExpenses() {
        return expenseRepository.findByDeletedFalse();
    }

    public Expense getExpenseById(Long id) {
        Expense expense = expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));

        if (Boolean.TRUE.equals(expense.getDeleted())) {
            throw new ResourceNotFoundException("Expense not found with id: " + id);
        }

        return expense;
    }

    public Expense updateExpense(Long id, Expense updatedExpense) {
        Expense existing = getExpenseById(id);

        existing.setDescription(updatedExpense.getDescription());
        existing.setAmount(updatedExpense.getAmount());
        existing.setCategory(updatedExpense.getCategory());
        existing.setExpenseDate(updatedExpense.getExpenseDate());
        existing.setStatus(updatedExpense.getStatus());

        return expenseRepository.save(existing);
    }

    public void deleteExpense(Long id) {
        Expense existing = getExpenseById(id);
        existing.setDeleted(true);
        expenseRepository.save(existing);

        String performedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogService.logAction("SOFT_DELETE_EXPENSE", "Expense", id, performedBy,
                "Soft-deleted expense: " + existing.getDescription());
    }

    private void checkForAnomaly(Expense expense) {
        List<Expense> pastExpenses = expenseRepository.findAll().stream()
                .filter(e -> e.getVendor() != null
                        && expense.getVendor() != null
                        && e.getVendor().getId().equals(expense.getVendor().getId())
                        && !e.getId().equals(expense.getId()))
                .toList();

        if (pastExpenses.isEmpty()) {
            return; // no history to compare against yet
        }

        double average = pastExpenses.stream()
                .mapToDouble(e -> e.getAmount().doubleValue())
                .average()
                .orElse(0.0);

        if (expense.getAmount().doubleValue() > average * 2) {
            AnomalyFlag flag = new AnomalyFlag();
            flag.setEntityType("Expense");
            flag.setEntityId(expense.getId());
            flag.setReason("Amount ₹" + expense.getAmount() + " is more than double the vendor's average of ₹" + average);
            flag.setSeverity("MEDIUM");
            anomalyFlagService.createFlag(flag);
        }
    }
}