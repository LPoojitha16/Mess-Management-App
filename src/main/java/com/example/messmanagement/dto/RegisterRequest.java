package com.example.messmanagement.dto;

public class RegisterRequest {
    private String regNo;
    private String name;
    private String email;
    private String username;
    private String password;
    private String role;

    public RegisterRequest() {}

    public RegisterRequest(String regNo, String name, String email, String username, String password, String role) {
        this.regNo = regNo;
        this.name = name;
        this.email = email;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters and setters
    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RegisterRequest{regNo='" + regNo + "', name='" + name + "', email='" + email + "', username='" + username + "', password='" + password + "', role='" + role + "'}";
    }
}