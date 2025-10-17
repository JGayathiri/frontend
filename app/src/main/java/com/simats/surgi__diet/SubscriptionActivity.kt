package com.simats.surgi__diet

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class SubscriptionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_subscription)

        // Window inset adjustment for root layout
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainContainer)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Button references
        val btnSubscribe = findViewById<MaterialButton>(R.id.btnSubscribe)
        val btnSkipForNow = findViewById<MaterialButton>(R.id.btnSkipForNow)

        // Subscribe button click
        btnSubscribe.setOnClickListener {
            Toast.makeText(this, "Subscribed to Recovery Plus!", Toast.LENGTH_SHORT).show()
            // Example: redirect to payment or main activity
            // startActivity(Intent(this, PaymentActivity::class.java))
        }

        // Skip button click → Navigate to RoleActivity
        btnSkipForNow.setOnClickListener {
            val intent = Intent(this, RoleActivity::class.java)
            startActivity(intent)
            finish() // optional: close current activity
        }
    }
}
