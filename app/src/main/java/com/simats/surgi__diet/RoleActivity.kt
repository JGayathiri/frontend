package com.simats.surgi__diet

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.cardview.widget.CardView

class RoleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_role)

        // Handle window insets for edge-to-edge
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Get references to role cards
        val cardPatient: CardView = findViewById(R.id.cardPatient)
        val cardDoctor: CardView = findViewById(R.id.cardDoctor)
        val cardAdmin: CardView = findViewById(R.id.cardAdmin)

        // Set click listeners to navigate to LoginActivity
        cardPatient.setOnClickListener { goToLogin("Patient") }
        cardDoctor.setOnClickListener { goToLogin("Doctor") }
        cardAdmin.setOnClickListener { goToLogin("Admin") }
    }

    // Function to navigate to LoginActivity with optional role
    private fun goToLogin(role: String) {
        val intent = Intent(this, LoginActivity::class.java)
        intent.putExtra("ROLE", role) // Pass role to LoginActivity
        startActivity(intent)
    }
}
