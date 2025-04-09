package com.example.messmanagement

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.recyclerview.widget.RecyclerView
import com.example.messmanagement.api.ApiService
import com.example.messmanagement.model.Permission
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PermissionAdapter(
    private var permissions: List<Permission>,
    private val token: String,
    private val lifecycleScope: LifecycleCoroutineScope // Pass lifecycleScope
) : RecyclerView.Adapter<PermissionAdapter.PermissionViewHolder>() {

    private val apiService: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(ApiService::class.java)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PermissionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_permission, parent, false)
        return PermissionViewHolder(view)
    }

    override fun onBindViewHolder(holder: PermissionViewHolder, position: Int) {
        val permission = permissions[position]
        holder.tvRegNo.text = "Reg No: ${permission.regNo}"
        holder.tvStatus.text = "Status: ${permission.status}"

        holder.btnApprove.setOnClickListener {
            if (permission.id == null) {
                Toast.makeText(holder.itemView.context, "Permission ID not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                try {
                    val response = apiService.approvePermission("Bearer $token", permission.id)
                    if (response.isSuccessful) {
                        Toast.makeText(holder.itemView.context, "Permission approved!", Toast.LENGTH_SHORT).show()
                        holder.tvStatus.text = "Status: approved"
                    } else {
                        Toast.makeText(holder.itemView.context, "Failed to approve: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(holder.itemView.context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }

        holder.btnDisapprove.setOnClickListener {
            if (permission.id == null) {
                Toast.makeText(holder.itemView.context, "Permission ID not found", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            lifecycleScope.launch {
                try {
                    val response = apiService.disapprovePermission("Bearer $token", permission.id)
                    if (response.isSuccessful) {
                        Toast.makeText(holder.itemView.context, "Permission disapproved!", Toast.LENGTH_SHORT).show()
                        holder.tvStatus.text = "Status: disapproved"
                    } else {
                        Toast.makeText(holder.itemView.context, "Failed to disapprove: ${response.code()}", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(holder.itemView.context, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    override fun getItemCount(): Int = permissions.size

    fun updatePermissions(newPermissions: List<Permission>) {
        permissions = newPermissions
        notifyDataSetChanged()
    }

    class PermissionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvRegNo: TextView = itemView.findViewById(R.id.tvRegNo) // Display Reg No
        val tvStatus: TextView = itemView.findViewById(R.id.tvStatus)
        val btnApprove: Button = itemView.findViewById(R.id.btnApprove)
        val btnDisapprove: Button = itemView.findViewById(R.id.btnDisapprove)
    }
}
