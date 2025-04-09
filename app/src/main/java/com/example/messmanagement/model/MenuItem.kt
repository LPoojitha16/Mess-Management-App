package com.example.messmanagement.model

data class MenuItem(
    val id: Long? = null, // Optional, set by the server
    val itemName: String, // Name of the menu item
    val price: Int // Price of the menu item
)