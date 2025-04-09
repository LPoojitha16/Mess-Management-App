package com.example.messmanagement.service;

import com.example.messmanagement.dto.RegisterRequest;
import com.example.messmanagement.dto.RegisterResponse;
import com.example.messmanagement.model.User;

import java.util.Optional;

public interface UserService {
    void saveUser(User user);
    Optional<User> findByUsername(String username);
    RegisterResponse register(RegisterRequest request); // Add register method
}