package com.simats.surgi__diet

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Admin1Activity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var btnApprove: Button
    private lateinit var btnReject: Button

    private lateinit var statusBadge: TextView
    private lateinit var doctorName: TextView
    private lateinit var doctorSpecialty: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin1)

        // Edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        backArrow = findViewById(R.id.backArrow)
        btnApprove = findViewById(R.id.btnApprove)
        btnReject = findViewById(R.id.btnReject)
        statusBadge = findViewById(R.id.statusBadge)
        doctorName = findViewById(R.id.doctorName)
        doctorSpecialty = findViewById(R.id.doctorSpecialty)

        // Back button click
        backArrow.setOnClickListener {
            finish() // Go back to previous screen
        }

        // Approve button click
        btnApprove.setOnClickListener {
            statusBadge.text = "Approved"
            statusBadge.setBackgroundResource(R.drawable.bg_approved_badge) // Make sure drawable exists
            Toast.makeText(this, "${doctorName.text} approved!", Toast.LENGTH_SHORT).show()
        }

        // Reject button click
        btnReject.setOnClickListener {
            statusBadge.text = "Rejected"
            statusBadge.setBackgroundResource(R.drawable.bg_rejected_badge) // Make sure drawable exists
            Toast.makeText(this, "${doctorName.text} rejected!", Toast.LENGTH_SHORT).show()
        }
    }
}
