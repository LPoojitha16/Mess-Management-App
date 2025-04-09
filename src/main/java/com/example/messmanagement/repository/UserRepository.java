package com.example.messmanagement.repository;

import com.example.messmanagement.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email); // Add this
    Optional<User> findByRegNo(String regNo); // Add this
}