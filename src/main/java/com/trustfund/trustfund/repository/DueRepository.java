package com.trustfund.trustfund.repository;

import com.trustfund.trustfund.entity.Due;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DueRepository extends JpaRepository<Due, Long> {

    List<Due> findByUserId(Long userId);

    List<Due> findBySocietyId(Long societyId);

    List<Due> findByStatus(String status);

    List<Due> findByDeletedFalse();
}