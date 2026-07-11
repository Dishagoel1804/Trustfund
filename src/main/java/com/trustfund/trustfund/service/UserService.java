package com.trustfund.trustfund.service;

import com.trustfund.trustfund.entity.Society;
import com.trustfund.trustfund.entity.User;
import com.trustfund.trustfund.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.trustfund.trustfund.exception.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuditLogService auditLogService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
    }

    public User updateUser(Long id, User updatedUser) {
        User existing = getUserById(id); // reuses what we just wrote

        existing.setFullName(updatedUser.getFullName());
        existing.setFlatNumber(updatedUser.getFlatNumber());
        existing.setEmail(updatedUser.getEmail());

        return userRepository.save(existing);
    }

    public void deleteUser(Long id) {
        User existing = getUserById(id);
        userRepository.delete(existing);

        String performedBy = SecurityContextHolder.getContext().getAuthentication().getName();
        auditLogService.logAction("DELETE_USER", "User", id, performedBy,
                "Deleted user: " + existing.getFullName());
    }
}