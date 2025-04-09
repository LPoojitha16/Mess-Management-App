package com.example.messmanagement

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.messmanagement.api.ApiService
import com.example.messmanagement.model.Order
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ManageOrdersActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manage_orders)

        val sharedPref = getSharedPreferences("user_prefs", MODE_PRIVATE)
        val userRole = sharedPref.getString("user_role", "student") ?: "student"
        val token = sharedPref.getString("jwtToken", "") ?: ""

        if (userRole != "admin") {
            Toast.makeText(this, "Only admins can manage orders", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        val retrofit = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8081/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val apiService = retrofit.create(ApiService::class.java)

        findViewById<TextView>(R.id.etNewItemName).visibility = View.GONE
        findViewById<TextView>(R.id.etNewItemPrice).visibility = View.GONE
        findViewById<Button>(R.id.btnAddItem).visibility = View.GONE
        findViewById<LinearLayout>(R.id.llMenuItems).visibility = View.GONE
        findViewById<TextView>(R.id.tvManageMenuItems).visibility = View.GONE

        val tvOrders = findViewById<TextView>(R.id.tvOrders)
        val llOrdersContainer = findViewById<LinearLayout>(R.id.llOrdersContainer)

        // Use coroutine to call the suspend function getAllOrders
        lifecycleScope.launch {
            try {
                val response = apiService.getAllOrders("Bearer $token")
                if (response.isSuccessful) {
                    val orders = response.body() ?: emptyList()
                    if (orders.isEmpty()) {
                        tvOrders.text = "No orders found."
                    } else {
                        tvOrders.visibility = View.GONE
                        val llOrders = LinearLayout(this@ManageOrdersActivity).apply {
                            orientation = LinearLayout.VERTICAL
                        }
                        orders.forEach { order ->
                            val orderLayout = LinearLayout(this@ManageOrdersActivity).apply {
                                orientation = LinearLayout.VERTICAL
                                setPadding(0, 16, 0, 16)
                            }
                            val tvOrder = TextView(this@ManageOrdersActivity).apply {
                                text = "User: ${order.user?.username}, Items: ${order.items}, Qty: ${order.quantity}, Paid: ${order.paymentStatus}"
                                textSize = 16f
                            }
                            val btnTogglePayment = Button(this@ManageOrdersActivity).apply {
                                text = if (order.paymentStatus) "Mark Unpaid" else "Mark Paid"
                                setBackgroundResource(android.R.color.black)
                                setTextColor(resources.getColor(android.R.color.white))
                                setOnClickListener {
                                    // Use coroutine to call the suspend function updateOrderStatus
                                    lifecycleScope.launch {
                                        try {
                                            val newStatus = if (order.paymentStatus) "false" else "true"
                                            val updateResponse = apiService.updateOrderStatus(
                                                order.id!!,
                                                newStatus,
                                                "Bearer $token"
                                            )
                                            if (updateResponse.isSuccessful) {
                                                val updatedOrder = updateResponse.body()
                                                if (updatedOrder != null) {
                                                    text = if (updatedOrder.paymentStatus) "Mark Unpaid" else "Mark Paid"
                                                    tvOrder.text = "User: ${updatedOrder.user?.username}, Items: ${updatedOrder.items}, Qty: ${updatedOrder.quantity}, Paid: ${updatedOrder.paymentStatus}"
                                                }
                                            } else {
                                                Toast.makeText(
                                                    this@ManageOrdersActivity,
                                                    "Failed to update: ${updateResponse.code()}",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            }
                                        } catch (e: Exception) {
                                            Toast.makeText(
                                                this@ManageOrdersActivity,
                                                "Error: ${e.message}",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                }
                            }
                            orderLayout.addView(tvOrder)
                            orderLayout.addView(btnTogglePayment)
                            llOrders.addView(orderLayout)
                        }
                        llOrdersContainer.addView(llOrders)

                        // Update the back button to navigate to HomeActivity
                        val btnBack = Button(this@ManageOrdersActivity).apply {
                            text = "Back"
                            setBackgroundResource(android.R.color.black)
                            setTextColor(resources.getColor(android.R.color.white))
                            layoutParams = LinearLayout.LayoutParams(
                                LinearLayout.LayoutParams.MATCH_PARENT,
                                LinearLayout.LayoutParams.WRAP_CONTENT
                            ).apply { topMargin = 16 }
                            setOnClickListener {
                                startActivity(Intent(this@ManageOrdersActivity, HomeActivity::class.java))
                                finish()
                            }
                        }
                        llOrdersContainer.addView(btnBack)
                    }
                } else {
                    tvOrders.text = "Failed to load: ${response.code()}"
                }
            } catch (e: Exception) {
                tvOrders.text = "Error: ${e.message}"
            }
        }
    }
}