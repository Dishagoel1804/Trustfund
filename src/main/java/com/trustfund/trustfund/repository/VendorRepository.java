package com.trustfund.trustfund.repository;

import com.trustfund.trustfund.entity.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VendorRepository extends JpaRepository<Vendor, Long> {
}