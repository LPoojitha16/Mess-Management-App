package com.example.messmanagement

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.messmanagement.api.ApiService
import com.example.messmanagement.api.RetrofitClient
import com.example.messmanagement.model.LoginRequest
import com.example.messmanagement.model.LoginResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LoginActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var etUsername: EditText
    private lateinit var etPassword: EditText
    private lateinit var btnLogin: Button
    private lateinit var tvRegister: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Initialize SharedPreferences
        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // Initialize API service
        apiService = RetrofitClient.apiService

        // Initialize UI elements
        etUsername = findViewById(R.id.etUsername)
        etPassword = findViewById(R.id.etPassword)
        btnLogin = findViewById(R.id.btnLogin)
        tvRegister = findViewById(R.id.tvRegister)

        // Set click listener for login button
        btnLogin.setOnClickListener {
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val loginRequest = LoginRequest(username, password)

            // Perform login API call
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = apiService.login(loginRequest)
                    withContext(Dispatchers.Main) { // Switch to main thread for UI updates
                        if (response.isSuccessful) {
                            val loginResponse = response.body()
                            loginResponse?.let {
                                // Save token, username, and role to SharedPreferences
                                sharedPreferences.edit()
                                    .putString("token", it.token)
                                    .putString("username", it.username)
                                    .putString("role", it.role)
                                    .apply()

                                // Log the role to confirm
                                println("Saved role in LoginActivity: ${it.role}")

                                // Show success message
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Login successful: ${it.message}",
                                    Toast.LENGTH_SHORT
                                ).show()

                                // Navigate to HomeActivity
                                val intent = Intent(this@LoginActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finish() // Close LoginActivity
                            }
                        } else {
                            // Show error message
                            Toast.makeText(
                                this@LoginActivity,
                                "Login failed: ${response.code()} - ${response.message()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) { // Switch to main thread for UI updates
                        // Show error message
                        Toast.makeText(
                            this@LoginActivity,
                            "Error during login: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        // Set click listener for register TextView
        tvRegister.setOnClickListener {
            // Navigate to RegisterActivity
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }
    }
}