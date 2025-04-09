package com.example.messmanagement

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userRole = sharedPref.getString("user_role", "student") ?: "student"

        val btnMenu = findViewById<Button>(R.id.btnMenu)
        val btnOrders = findViewById<Button>(R.id.btnOrders)
        val btnForm = findViewById<Button>(R.id.btnForm)
        val btnManageOrders = findViewById<Button>(R.id.btnManageOrders)
        val btnManagePermissions = findViewById<Button>(R.id.btnManagePermissions)
        val btnLogout = findViewById<Button>(R.id.btnLogout)

        // Show/hide buttons based on role
        if (userRole == "admin") {
            btnMenu.visibility = View.VISIBLE
            btnOrders.visibility = View.GONE
            btnForm.visibility = View.GONE
            btnManageOrders.visibility = View.VISIBLE
            btnManagePermissions.visibility = View.VISIBLE
        } else { // student
            btnMenu.visibility = View.VISIBLE
            btnOrders.visibility = View.VISIBLE
            btnForm.visibility = View.VISIBLE
            btnManageOrders.visibility = View.GONE
            btnManagePermissions.visibility = View.GONE
        }

        btnMenu.setOnClickListener {
            startActivity(Intent(this, MenuActivity::class.java))
        }
        btnOrders.setOnClickListener {
            startActivity(Intent(this, OrdersActivity::class.java))
        }
        btnForm.setOnClickListener {
            startActivity(Intent(this, FormActivity::class.java))
        }
        btnManageOrders.setOnClickListener {
            startActivity(Intent(this, ManageOrdersActivity::class.java))
        }
        btnManagePermissions.setOnClickListener {
            startActivity(Intent(this, ManagePermissionsActivity::class.java))
        }
        btnManagePermissions.setOnClickListener {
            startActivity(Intent(this, PermissionAdapter::class.java))
        }


        // Logout functionality
        btnLogout.setOnClickListener {
            // Clear SharedPreferences
            with(sharedPref.edit()) {
                clear()
                apply()
            }
            // Navigate to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()
        }
    }
}