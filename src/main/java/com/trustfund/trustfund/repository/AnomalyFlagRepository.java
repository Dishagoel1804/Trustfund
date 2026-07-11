package com.trustfund.trustfund.repository;

import com.trustfund.trustfund.entity.AnomalyFlag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnomalyFlagRepository extends JpaRepository<AnomalyFlag, Long> {

    List<AnomalyFlag> findByStatus(String status);

    List<AnomalyFlag> findBySeverity(String severity);

    List<AnomalyFlag> findByEntityTypeAndEntityId(String entityType, Long entityId);
}