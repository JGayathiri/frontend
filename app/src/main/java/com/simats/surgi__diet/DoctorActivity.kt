package com.simats.surgi__diet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DoctorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctor)

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Input fields ---
        val usernameInput: EditText = findViewById(R.id.nameInput)
        val emailInput: EditText = findViewById(R.id.emailInput)
        val passwordInput: EditText = findViewById(R.id.passwordInput)
        val hospitalInput: EditText = findViewById(R.id.hospitalInput)
        val licenseInput: EditText = findViewById(R.id.licenseInput)
        val yearsInput: EditText = findViewById(R.id.yearsInput)
        val createAccountButton: Button = findViewById(R.id.createAccountButton)

        createAccountButton.setOnClickListener {
            val doctorName = usernameInput.text.toString().trim()
            val email = emailInput.text.toString().trim()
            val password = passwordInput.text.toString().trim()
            val hospital = hospitalInput.text.toString().trim()
            val license = licenseInput.text.toString().trim()
            val years = yearsInput.text.toString().trim()

            // --- Validation ---
            when {
                doctorName.isEmpty() -> {
                    usernameInput.error = "Enter full name"
                    usernameInput.requestFocus()
                    return@setOnClickListener
                }
                email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() -> {
                    emailInput.error = "Enter valid email"
                    emailInput.requestFocus()
                    return@setOnClickListener
                }
                password.isEmpty() || password.length < 6 -> {
                    passwordInput.error = "Password must be at least 6 characters"
                    passwordInput.requestFocus()
                    return@setOnClickListener
                }
                hospital.isEmpty() -> {
                    hospitalInput.error = "Enter hospital/clinic name"
                    hospitalInput.requestFocus()
                    return@setOnClickListener
                }
                license.isEmpty() -> {
                    licenseInput.error = "Enter medical license ID"
                    licenseInput.requestFocus()
                    return@setOnClickListener
                }
                years.isEmpty() || !years.all { it.isDigit() } -> {
                    yearsInput.error = "Enter valid years of experience"
                    yearsInput.requestFocus()
                    return@setOnClickListener
                }
            }

            // --- Example Patient Data ---
            val patientName = "Arjun"             // Replace with actual patient info if needed
            val surgery = "Knee Surgery"
            val date = "2023-07-15"
            val diabetes = false
            val foodPrefs = arrayOf("Low Carb", "High Protein")
            val loggedMeals = 4
            val totalMeals = 5

            // --- Start Doctor1Activity with Intent ---
            val intent = Intent(this, Doctor1Activity::class.java)
            intent.putExtra("DOCTOR_NAME", doctorName)
            intent.putExtra("PATIENT_NAME", patientName)
            intent.putExtra("SURGERY", surgery)
            intent.putExtra("DATE", date)
            intent.putExtra("DIABETES", diabetes)
            intent.putExtra("FOOD_PREFS", foodPrefs)
            intent.putExtra("LOGGED_MEALS", loggedMeals)
            intent.putExtra("TOTAL_MEALS", totalMeals)
            startActivity(intent)
        }
    }
}
