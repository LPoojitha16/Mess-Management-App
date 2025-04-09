package com.example.messmanagement.model

data class User(
    val id: Long? = null,
    val username: String,
    val password: String? = null, // Password might not be needed in responses
    val role: String
)