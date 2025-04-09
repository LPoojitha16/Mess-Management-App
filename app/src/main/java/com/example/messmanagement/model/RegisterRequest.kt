package com.example.messmanagement.model

data class RegisterRequest(
    val regNo: String,
    val name: String,
    val email: String,
    val username: String,
    val password: String,
    val role: String
)