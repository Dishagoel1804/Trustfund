package com.trustfund.trustfund.repository;

import com.trustfund.trustfund.entity.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> {
}