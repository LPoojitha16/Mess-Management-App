package com.example.messmanagement

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide // Corrected import

class QRActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qr)

        val totalAmount = intent.getIntExtra("total_amount", 0)

        val ivQRCode: ImageView = findViewById(R.id.ivQRCode)
        val btnPay: Button = findViewById(R.id.btnPay)

        // Load the QR code image using Glide
        Glide.with(this)
            .load(R.drawable.qr_code) // Ensure qr_code.png exists in res/drawable/
            .into(ivQRCode)

        // Pay button
        btnPay.setOnClickListener {
            Toast.makeText(this, "Payment of ₹$totalAmount initiated!", Toast.LENGTH_SHORT).show()
            // Add your payment logic here (e.g., navigate to a success page)
        }
    }
}