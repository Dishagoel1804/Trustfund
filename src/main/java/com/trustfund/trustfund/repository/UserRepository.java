package com.trustfund.trustfund.repository;

import com.trustfund.trustfund.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByEmail(String email);
}