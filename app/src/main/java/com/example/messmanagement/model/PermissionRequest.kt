package com.example.messmanagement.model

data class PermissionRequest(
    val RegNo: Long,
    val reason: String,
    val status: String
)
