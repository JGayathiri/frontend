package com.simats.surgi__diet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class OtpActivity : AppCompatActivity() {

    private var generatedOtp: String = ""   // Random OTP will be stored here
    private var role: String = "Patient"
    private lateinit var otpInput: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_otp)

        // Insets
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Role from intent
        role = intent.getStringExtra("ROLE") ?: "Patient"

        // Views
        otpInput = findViewById(R.id.otpInput)
        val btnVerify: Button = findViewById(R.id.btnVerifyOtp)

        // Generate a random OTP each time
        generatedOtp = (1000..9999).random().toString()

        // ✅ No Toast, no auto-fill → user must type the OTP manually

        // Verify button
        btnVerify.setOnClickListener {
            val enteredOtp = otpInput.text.toString().trim()
            if (enteredOtp == generatedOtp) {
                Toast.makeText(this, "OTP Verified!", Toast.LENGTH_SHORT).show()

                // Navigate based on role
                val nextIntent = when (role) {
                    "Patient" -> Intent(this, HomeActivity::class.java)
                    "Doctor" -> Intent(this, Doctor1Activity::class.java)
                    "Admin" -> Intent(this, AdminActivity::class.java)
                    else -> Intent(this, HomeActivity::class.java)
                }
                startActivity(nextIntent)
                finish()
            } else {
                Toast.makeText(this, "Invalid OTP. Try again.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
