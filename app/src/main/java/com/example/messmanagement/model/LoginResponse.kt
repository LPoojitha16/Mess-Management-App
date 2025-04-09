package com.example.messmanagement.model

data class LoginResponse(
    val message: String,
    val username: String,
    val role: String,
    val token: String
)