package com.example.messmanagement.service;

import com.example.messmanagement.dto.RegisterRequest;
import com.example.messmanagement.dto.RegisterResponse;
import com.example.messmanagement.model.User;
import com.example.messmanagement.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void saveUser(User user) {
        userRepository.save(user);
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public RegisterResponse register(RegisterRequest request) {
        // Log the incoming request
        System.out.println("Register Request: " + request);

        // Validate null or empty fields
        if (request.getUsername() == null || request.getUsername().isEmpty()) {
            return new RegisterResponse("Username cannot be empty", null, null);
        }
        if (request.getEmail() == null || request.getEmail().isEmpty()) {
            return new RegisterResponse("Email cannot be empty", null, null);
        }
        if (request.getRegNo() == null || request.getRegNo().isEmpty()) {
            return new RegisterResponse("Registration number cannot be empty", null, null);
        }
        if (request.getPassword() == null || request.getPassword().isEmpty()) {
            return new RegisterResponse("Password cannot be empty", null, null);
        }

        // Check if username already exists
        Optional<User> existingUserByUsername = userRepository.findByUsername(request.getUsername());
        if (existingUserByUsername.isPresent()) {
            System.out.println("Existing user found by username: " + existingUserByUsername.get());
            return new RegisterResponse("Username already exists", null, null);
        }

        // Check if email already exists
        Optional<User> existingUserByEmail = userRepository.findByEmail(request.getEmail());
        if (existingUserByEmail.isPresent()) {
            System.out.println("Existing user found by email: " + existingUserByEmail.get());
            return new RegisterResponse("Email already exists", null, null);
        }

        // Check if regNo already exists
        Optional<User> existingUserByRegNo = userRepository.findByRegNo(request.getRegNo());
        if (existingUserByRegNo.isPresent()) {
            System.out.println("Existing user found by regNo: " + existingUserByRegNo.get());
            return new RegisterResponse("Registration number already exists", null, null);
        }

        // Validate password length
        if (request.getPassword().length() < 6) {
            return new RegisterResponse("Password must be at least 6 characters long", null, null);
        }

        // Create new user
        User user = new User();
        user.setRegNo(request.getRegNo());
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(request.getRole() != null ? request.getRole().toUpperCase() : "USER");

        // Save user to database
        userRepository.save(user);

        // Log the saved user
        System.out.println("User saved: " + user);

        return new RegisterResponse("User registered successfully", user.getUsername(), user.getRole());
    }
}