package com.trustfund.trustfund.service;

import com.trustfund.trustfund.entity.Expense;
import com.trustfund.trustfund.repository.ExpenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trustfund.trustfund.exception.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
public class ExpenseService {

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    private AuditLogService auditLogService;


    public Expense createExpense(Expense expense) {
        Expense saved = expenseRepository.save(expense);

        String performedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogService.logAction("CREATE_EXPENSE", "Expense", saved.getId(), performedBy,
                "Created expense: " + saved.getDescription() + ", amount: " + saved.getAmount());

        return saved;
    }
    public List<Expense> getAllExpenses() {
        return expenseRepository.findAll();
    }

    public Expense getExpenseById(Long id) {
        return expenseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Expense not found with id: " + id));
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
        expenseRepository.delete(existing);

        String performedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogService.logAction("DELETE_EXPENSE", "Expense", id, performedBy,
                "Deleted expense: " + existing.getDescription());
    }
}