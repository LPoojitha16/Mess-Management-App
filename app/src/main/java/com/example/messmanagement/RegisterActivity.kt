package com.example.messmanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.messmanagement.api.RetrofitClient
import com.example.messmanagement.model.RegisterRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val etRegNo = findViewById<EditText>(R.id.etRegNo)
        val etName = findViewById<EditText>(R.id.etName)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnRegister = findViewById<Button>(R.id.btnRegister)
        val tvLogin = findViewById<TextView>(R.id.tvLogin)

        btnRegister.setOnClickListener {
            val regNo = etRegNo.text.toString().trim()
            val name = etName.text.toString().trim()
            val email = etEmail.text.toString().trim()
            val username = etUsername.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (regNo.isEmpty() || name.isEmpty() || email.isEmpty() || username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val registerRequest = RegisterRequest(regNo, name, email, username, password, "student")

            // Perform registration API call
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val response = RetrofitClient.apiService.register(registerRequest)
                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val registerResponse = response.body()
                            registerResponse?.let {
                                // Store username and role in SharedPreferences
                                val sharedPref = getSharedPreferences("MyPrefs", MODE_PRIVATE)
                                with(sharedPref.edit()) {
                                    putString("username", it.username)
                                    putString("role", it.role)
                                    apply()
                                }

                                Toast.makeText(this@RegisterActivity, "Registration successful: ${it.message}", Toast.LENGTH_SHORT).show()

                                // Navigate to HomeActivity
                                val intent = Intent(this@RegisterActivity, HomeActivity::class.java)
                                startActivity(intent)
                                finish()
                            }
                        } else {
                            Toast.makeText(
                                this@RegisterActivity,
                                "Registration failed: ${response.code()} - ${response.message()}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                } catch (e: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(
                            this@RegisterActivity,
                            "Error during registration: ${e.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }

        tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}