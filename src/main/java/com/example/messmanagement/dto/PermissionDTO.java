package com.example.messmanagement.dto;

public class PermissionDTO {

    private Long id;
    private String regNo;
    private String reason;
    private String status;

    // Constructors
    public PermissionDTO() {}

    public PermissionDTO(Long id, String regNo, String reason, String status) {
        this.id = id;
        this.regNo = regNo;
        this.reason = reason;
        this.status = status;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}