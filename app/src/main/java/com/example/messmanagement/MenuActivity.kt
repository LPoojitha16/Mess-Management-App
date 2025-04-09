package com.example.messmanagement

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.messmanagement.R

class MenuActivity : AppCompatActivity() {

    private lateinit var tvMenu: TextView
    private lateinit var btnEditMenu: Button
    private lateinit var sharedPreferences: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // Initialize UI elements
        tvMenu = findViewById(R.id.tvMenu)
        btnEditMenu = findViewById(R.id.btnEditMenu)

        // Default static menu (used only if nothing is saved in SharedPreferences)
        val defaultMenuText = """
            Today's Menu:

            Breakfast (8:00 AM - 9:30 AM):
            - Idli with Sambar
            - Vada
            - Tea/Coffee

            Lunch (12:30 PM - 2:00 PM):
            - Rice
            - Dal
            - Mixed Vegetable Curry
            - Chapati
            - Papad

            Dinner (7:30 PM - 9:00 PM):
            - Fried Rice
            - Manchurian
            - Raita
            - Sweet (Gulab Jamun)
        """.trimIndent()

        // Load menu from SharedPreferences, or use default if not found
        val savedMenu = sharedPreferences.getString("menuText", null)
        tvMenu.text = savedMenu ?: defaultMenuText

        // Check user role and show/hide edit button
        val userRole = sharedPreferences.getString("role", "ROLE_student") // Returns String?
        Log.d("MenuActivity", "User role in MenuActivity: $userRole") // Log the role for debugging
        if (userRole?.trim()?.equals("ROLE_admin", ignoreCase = true) == true) {
            Log.d("MenuActivity", "Admin role detected, showing edit button")
            btnEditMenu.visibility = View.VISIBLE // Show the edit button for admins
        } else {
            Log.d("MenuActivity", "Not an admin, hiding edit button. Role: $userRole")
            btnEditMenu.visibility = View.GONE // Hide the edit button for non-admins
        }

        // Set click listener for the edit button
        btnEditMenu.setOnClickListener {
            // Show a dialog to edit the menu
            val dialogView = layoutInflater.inflate(R.layout.dialog_edit_menu, null)
            val etMenu = dialogView.findViewById<EditText>(R.id.etMenu)
            etMenu.setText(tvMenu.text) // Pre-fill the EditText with the current menu

            AlertDialog.Builder(this)
                .setTitle("Edit Menu")
                .setView(dialogView)
                .setPositiveButton("Save") { _, _ ->
                    // Update the menu text and save to SharedPreferences
                    val updatedMenu = etMenu.text.toString()
                    if (updatedMenu.isNotEmpty()) {
                        tvMenu.text = updatedMenu
                        // Save to SharedPreferences
                        with(sharedPreferences.edit()) {
                            putString("menuText", updatedMenu)
                            apply() // Asynchronous save
                        }
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }
}