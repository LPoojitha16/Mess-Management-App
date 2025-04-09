package com.example.messmanagement.dto;

public class RegisterResponse {
    private String message;
    private String username;
    private String role;

    public RegisterResponse(String message, String username, String role) {
        this.message = message;
        this.username = username;
        this.role = role;
    }

    // Getters and setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}