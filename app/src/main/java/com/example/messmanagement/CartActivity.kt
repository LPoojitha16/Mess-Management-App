package com.example.messmanagement

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class CartActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        val quantityFriedRice = intent.getIntExtra("quantity_fried_rice", 0)
        val quantityNoodles = intent.getIntExtra("quantity_noodles", 0)

        val llCartItems: LinearLayout = findViewById(R.id.llCartItems)
        val tvTotalAmount: TextView = findViewById(R.id.tvTotalAmount)
        val btnPayNow: Button = findViewById(R.id.btnPayNow)

        var totalAmount = 0

        // Display Fried Rice if quantity > 0
        if (quantityFriedRice > 0) {
            val itemPrice = 50 // Price of Fried Rice
            val itemTotalPrice = quantityFriedRice * itemPrice
            totalAmount += itemTotalPrice

            val itemLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(0, 16, 0, 16)
            }

            val tvItemName = TextView(this).apply {
                text = "Fried Rice"
                textSize = 16f
                setTextColor(resources.getColor(android.R.color.black))
            }
            val tvItemQuantity = TextView(this).apply {
                text = "Quantity: $quantityFriedRice"
                textSize = 14f
                setTextColor(resources.getColor(android.R.color.black))
            }
            val tvItemPrice = TextView(this).apply {
                text = "Price: ₹$itemTotalPrice"
                textSize = 14f
                setTextColor(resources.getColor(android.R.color.black))
            }

            itemLayout.addView(tvItemName)
            itemLayout.addView(tvItemQuantity)
            itemLayout.addView(tvItemPrice)
            llCartItems.addView(itemLayout)
        }

        // Display Noodles if quantity > 0
        if (quantityNoodles > 0) {
            val itemPrice = 60 // Price of Noodles
            val itemTotalPrice = quantityNoodles * itemPrice
            totalAmount += itemTotalPrice

            val itemLayout = LinearLayout(this).apply {
                orientation = LinearLayout.VERTICAL
                setPadding(0, 16, 0, 16)
            }

            val tvItemName = TextView(this).apply {
                text = "Noodles"
                textSize = 16f
                setTextColor(resources.getColor(android.R.color.black))
            }
            val tvItemQuantity = TextView(this).apply {
                text = "Quantity: $quantityNoodles"
                textSize = 14f
                setTextColor(resources.getColor(android.R.color.black))
            }
            val tvItemPrice = TextView(this).apply {
                text = "Price: ₹$itemTotalPrice"
                textSize = 14f
                setTextColor(resources.getColor(android.R.color.black))
            }

            itemLayout.addView(tvItemName)
            itemLayout.addView(tvItemQuantity)
            itemLayout.addView(tvItemPrice)
            llCartItems.addView(itemLayout)
        }

        // Update total amount
        tvTotalAmount.text = "Total: ₹$totalAmount"

        // Pay Now button
        btnPayNow.setOnClickListener {
            val intent = Intent(this, QRActivity::class.java)
            intent.putExtra("total_amount", totalAmount)
            startActivity(intent)
        }
    }
}