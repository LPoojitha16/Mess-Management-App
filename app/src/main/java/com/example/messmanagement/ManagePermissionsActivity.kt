package com.example.messmanagement

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagement.api.RetrofitClient
import com.example.messmanagement.model.Permission
import kotlinx.coroutines.launch

class ManagePermissionsActivity : AppCompatActivity() {

    private lateinit var adapter: PermissionAdapter
    private lateinit var rvPermissions: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var btnRefresh: Button
    private lateinit var sharedPreferences: android.content.SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_permissions)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // Initialize views
        rvPermissions = findViewById(R.id.rvPermissions)
        progressBar = findViewById(R.id.progressBar)
        btnRefresh = findViewById(R.id.btnRefresh)

        // Initialize RecyclerView
        adapter = PermissionAdapter(emptyList(), sharedPreferences.getString("token", "") ?: "", lifecycleScope)
        rvPermissions.layoutManager = LinearLayoutManager(this)
        rvPermissions.adapter = adapter

        // Fetch permissions initially
        fetchPermissions()

        // Refresh button
        btnRefresh.setOnClickListener {
            fetchPermissions()
        }
    }

    private fun fetchPermissions() {
        progressBar.visibility = View.VISIBLE

        // Get token from SharedPreferences
        val token = sharedPreferences.getString("token", null)
        if (token == null) {
            Toast.makeText(this, "Please login first", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
            return
        }

        // Fetch permissions
        lifecycleScope.launch {
            try {
                val response = RetrofitClient.apiService.getPermissions("Bearer $token")
                progressBar.visibility = View.GONE
                if (response.isSuccessful) {
                    val permissions = response.body() ?: emptyList()
                    adapter.updatePermissions(permissions)
                } else {
                    Toast.makeText(this@ManagePermissionsActivity, "Failed to fetch permissions: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            } catch (e: Exception) {
                progressBar.visibility = View.GONE
                Toast.makeText(this@ManagePermissionsActivity, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}