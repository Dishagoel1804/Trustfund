package com.trustfund.trustfund.service;

import com.trustfund.trustfund.entity.Vendor;
import com.trustfund.trustfund.repository.VendorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trustfund.trustfund.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class VendorService {

    @Autowired
    private VendorRepository vendorRepository;

    public Vendor createVendor(Vendor vendor) {
        return vendorRepository.save(vendor);
    }

    public List<Vendor> getAllVendors() {
        return vendorRepository.findAll();
    }

    public Vendor getVendorById(Long id) {
        return vendorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vendor not found with id: " + id));
    }

    public Vendor updateVendor(Long id, Vendor updatedVendor) {
        Vendor existing = getVendorById(id);

        existing.setName(updatedVendor.getName());
        existing.setContactNumber(updatedVendor.getContactNumber());
        existing.setCategory(updatedVendor.getCategory());

        return vendorRepository.save(existing);
    }

    public void deleteVendor(Long id) {
        Vendor existing = getVendorById(id);
        vendorRepository.delete(existing);
    }
}