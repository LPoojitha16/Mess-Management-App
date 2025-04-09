package com.example.messmanagement.model

data class Order(
    val id: Long? = null, // Optional, set by the server
    val user: User? = null, // Optional, set by the server
    val items: String, // Comma-separated string of items (e.g., "Item1 x2, Item2 x1")
    val quantity: Int, // Total quantity of items
    val paymentStatus: Boolean // Payment status (true/false)
)