package com.trustfund.trustfund.service;

import com.trustfund.trustfund.entity.Society;
import com.trustfund.trustfund.repository.SocietyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.trustfund.trustfund.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class SocietyService {

    @Autowired
    private SocietyRepository societyRepository;

    public Society createSociety(Society society) {
        return societyRepository.save(society);
    }

    public List<Society> getAllSocieties() {
        return societyRepository.findAll();
    }

    public Society getSocietyById(Long id) {
        return societyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Society not found with id: " + id));
    }

    public Society updateSociety(Long id, Society updatedSociety) {
        Society existing = getSocietyById(id); // reuses what we just wrote

        existing.setName(updatedSociety.getName());
        existing.setAddress(updatedSociety.getAddress());
        existing.setRegistrationNumber(updatedSociety.getRegistrationNumber());

        return societyRepository.save(existing);
    }

    public void deleteSociety(Long id) {
        Society existing = getSocietyById(id); // throws if not found
        societyRepository.delete(existing);
    }
}