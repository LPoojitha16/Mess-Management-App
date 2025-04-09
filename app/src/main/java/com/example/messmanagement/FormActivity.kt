package com.example.messmanagement

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.messmanagement.api.RetrofitClient
import com.example.messmanagement.model.Permission
import com.example.messmanagement.model.PermissionResponse
import kotlinx.coroutines.launch

class FormActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // Check the user's role
        val role = sharedPreferences.getString("role", null)
        val token = sharedPreferences.getString("token", null)

        // Log the role and token for debugging
        Log.d("FormActivity", "Token: $token")
        Log.d("FormActivity", "Role: $role")

        if (token == null) {
            // If no token, redirect to LoginActivity
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        if (role == null) {
            // If role is not found, redirect to LoginActivity
            Toast.makeText(this, "User role not found. Please login again.", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        if (role == "ROLE_admin") {
            // If the user is an admin, redirect to ManagePermissionsActivity
            Log.d("FormActivity", "Admin detected, redirecting to ManagePermissionsActivity")
            startActivity(Intent(this, ManagePermissionsActivity::class.java))
            finish()
            return
        }

        // If the user is a student (ROLE_student), show the form
        val etRegNo: EditText = findViewById(R.id.etRegNo)
        val etReason: EditText = findViewById(R.id.etReason)
        val btnSubmit: Button = findViewById(R.id.btnSubmit)
        val btnViewPermissions: Button = findViewById(R.id.btnViewPermissions)

        btnSubmit.setOnClickListener {
            val regNo = etRegNo.text.toString().trim()
            val reason = etReason.text.toString().trim()

            if (regNo.isEmpty() || reason.isEmpty()) {
                Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Create permission object
            val permission = Permission(
                regNo = regNo,
                reason = reason,
                status = "pending"
            )

            // Make API call using coroutines
            lifecycleScope.launch {
                try {
                    val response = RetrofitClient.apiService.createPermission("Bearer $token", permission)
                    if (response.isSuccessful) {
                        val permissionResponse = response.body()
                        val permissionId = permissionResponse?.id
                        if (permissionId != null) {
                            Toast.makeText(this@FormActivity, "Permission submitted successfully!", Toast.LENGTH_SHORT).show()

                            // Navigate to PermissionStatusActivity with the permission ID
                            val intent = Intent(this@FormActivity, PermissionStatusActivity::class.java)
                            intent.putExtra("permission_id", permissionId)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@FormActivity, "Failed to get permission ID", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this@FormActivity, "Failed to submit: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(this@FormActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        // Navigate to StudentPermissionsActivity to view previous permissions
        btnViewPermissions.setOnClickListener {
            startActivity(Intent(this, PermissionStatusActivity::class.java))
        }
    }
}