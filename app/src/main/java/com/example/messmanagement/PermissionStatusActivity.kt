package com.example.messmanagement

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.messmanagement.api.RetrofitClient
import com.example.messmanagement.model.Permission
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class PermissionStatusActivity : AppCompatActivity() {

    private lateinit var tvPermissionStatus: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnRefresh: Button
    private lateinit var sharedPreferences: android.content.SharedPreferences
    private var pollingJob: Job? = null // To manage the polling coroutine

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_permission_status)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        tvPermissionStatus = findViewById(R.id.tvPermissionStatus)
        progressBar = findViewById(R.id.progressBar)
        btnRefresh = findViewById(R.id.btnRefresh)

        val permissionId = intent.getLongExtra("permission_id", -1)
        if (permissionId == -1L) {
            tvPermissionStatus.text = "Error: Permission ID not found"
            return
        }

        // Fetch status initially and start polling
        fetchPermissionStatus(permissionId)
        startPolling(permissionId)

        // Refresh button
        btnRefresh.setOnClickListener {
            fetchPermissionStatus(permissionId)
        }
    }

    private fun fetchPermissionStatus(permissionId: Long) {
        progressBar.visibility = View.VISIBLE

        // Get token from SharedPreferences
        val token = sharedPreferences.getString("token", null)
        if (token == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // Fetch the permission status
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getPermissionById("Bearer $token", permissionId)
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val permission = response.body()
                    val status = permission?.status ?: "unknown"
                    when (status) {
                        "pending" -> tvPermissionStatus.text = "Permission is still pending"
                        "approved" -> tvPermissionStatus.text = "Your permission is approved"
                        "disapproved" -> tvPermissionStatus.text = "Your permission is disapproved"
                        else -> tvPermissionStatus.text = "Permission status: $status"
                    }
                } else {
                    tvPermissionStatus.text = "Failed to fetch status: ${response.code()}"
                }
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                tvPermissionStatus.text = "Error: ${e.message}"
            }
        }
    }

    private fun startPolling(permissionId: Long) {
        pollingJob = lifecycleScope.launch {
            while (isActive) {
                fetchPermissionStatus(permissionId)
                delay(5000) // Poll every 5 seconds
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        pollingJob?.cancel() // Stop polling when the activity is destroyed
    }
}