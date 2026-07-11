package com.trustfund.trustfund.repository;

import com.trustfund.trustfund.entity.Dispute;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DisputeRepository extends JpaRepository<Dispute, Long> {

    List<Dispute> findByRaisedById(Long userId);

    List<Dispute> findByStatus(String status);
}