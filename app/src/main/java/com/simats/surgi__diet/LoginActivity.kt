package com.simats.surgi__diet

import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.util.Patterns
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LoginActivity : AppCompatActivity() {

    private var isPasswordVisible = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val usernameInput: EditText = findViewById(R.id.usernameInput)
        val passwordInput: EditText = findViewById(R.id.passwordInput)
        val eyeIcon: ImageView = findViewById(R.id.eyeIcon)
        val btnSignIn: Button = findViewById(R.id.btnSignIn)
        val signUpText: TextView = findViewById(R.id.signUpText)

        val role = intent.getStringExtra("ROLE") ?: "Patient"

        if (role == "Admin") signUpText.visibility = android.view.View.GONE

        // Password show/hide
        eyeIcon.setOnClickListener {
            if (isPasswordVisible) {
                passwordInput.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
            } else {
                passwordInput.inputType =
                    InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            }
            passwordInput.setSelection(passwordInput.text.length)
            isPasswordVisible = !isPasswordVisible
        }

        // Sign In Button Click
        btnSignIn.setOnClickListener {
            val email = usernameInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()

            // VALIDATION STARTS HERE
            if (email.isEmpty()) {
                usernameInput.error = "Email cannot be empty"
                return@setOnClickListener
            }

            if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                usernameInput.error = "Enter a valid email address"
                return@setOnClickListener
            }

            if (password.isEmpty()) {
                passwordInput.error = "Password cannot be empty"
                return@setOnClickListener
            }

            // Password must contain at least 8 characters, upper, lower, number, and special char
            val passwordPattern =
                "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@#\$%^&+=!]).{8,}\$"
            if (!password.matches(passwordPattern.toRegex())) {
                passwordInput.error =
                    "Password must contain 8+ chars, upper, lower, number & special symbol"
                return@setOnClickListener
            }

            // If all validation passed, proceed based on role
            when (role) {
                "Patient" -> startActivity(Intent(this, HomeActivity::class.java))
                "Doctor" -> startActivity(Intent(this, Doctor1Activity::class.java))
                "Admin" -> startActivity(Intent(this, AdminActivity::class.java))
                else -> Toast.makeText(this, "Unknown role!", Toast.LENGTH_SHORT).show()
            }
        }

        // Sign Up link
        signUpText.setOnClickListener {
            when (role) {
                "Patient" -> startActivity(Intent(this, SigninActivity::class.java))
                "Doctor" -> startActivity(Intent(this, DoctorActivity::class.java))
                else -> Toast.makeText(this, "Admin cannot sign up", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
