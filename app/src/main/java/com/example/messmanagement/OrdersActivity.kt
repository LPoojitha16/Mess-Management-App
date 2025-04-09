package com.example.messmanagement

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class OrdersActivity : AppCompatActivity() {

    private var quantityFriedRice = 0
    private var quantityNoodles = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orders)

        // Initialize SharedPreferences
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        // Check user role
        val userRole = sharedPreferences.getString("role", "ROLE_student")!!

        // UI elements
        val layoutOrderForm: LinearLayout = findViewById(R.id.layoutOrderForm)
        val tvNoOrders: TextView = findViewById(R.id.tvNoOrders)

        // Show/hide UI based on role
        if (userRole.trim().equals("ROLE_admin", ignoreCase = true)) {
            layoutOrderForm.visibility = View.GONE
            tvNoOrders.visibility = View.VISIBLE
        } else {
            layoutOrderForm.visibility = View.VISIBLE
            tvNoOrders.visibility = View.GONE

            // Fried Rice controls
            val tvQuantityFriedRice: TextView = findViewById(R.id.tvQuantityFriedRice)
            val btnPlusFriedRice: Button = findViewById(R.id.btnPlusFriedRice)
            val btnMinusFriedRice: Button = findViewById(R.id.btnMinusFriedRice)

            // Noodles controls
            val tvQuantityNoodles: TextView = findViewById(R.id.tvQuantityNoodles)
            val btnPlusNoodles: Button = findViewById(R.id.btnPlusNoodles)
            val btnMinusNoodles: Button = findViewById(R.id.btnMinusNoodles)

            // Go to Cart button
            val btnGoToCart: Button = findViewById(R.id.btnGoToCart)

            // Fried Rice quantity controls
            btnPlusFriedRice.setOnClickListener {
                quantityFriedRice++
                tvQuantityFriedRice.text = quantityFriedRice.toString()
            }

            btnMinusFriedRice.setOnClickListener {
                if (quantityFriedRice > 0) {
                    quantityFriedRice--
                    tvQuantityFriedRice.text = quantityFriedRice.toString()
                }
            }

            // Noodles quantity controls
            btnPlusNoodles.setOnClickListener {
                quantityNoodles++
                tvQuantityNoodles.text = quantityNoodles.toString()
            }

            btnMinusNoodles.setOnClickListener {
                if (quantityNoodles > 0) {
                    quantityNoodles--
                    tvQuantityNoodles.text = quantityNoodles.toString()
                }
            }

            // Go to Cart button
            btnGoToCart.setOnClickListener {
                val intent = Intent(this, CartActivity::class.java)
                intent.putExtra("quantity_fried_rice", quantityFriedRice)
                intent.putExtra("quantity_noodles", quantityNoodles)
                startActivity(intent)
            }
        }
    }
}