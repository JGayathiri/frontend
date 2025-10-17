package com.simats.surgi__diet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Doctor1Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctor1)

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // -------- Get data from Intent --------
        val doctorName = intent.getStringExtra("DOCTOR_NAME") ?: "Doctor Name"
        findViewById<TextView>(R.id.welcome_text).text = "Welcome, Dr. $doctorName 👋"

        // Multiple patients passed as arrays
        val patientNames = intent.getStringArrayExtra("PATIENT_NAMES") ?: arrayOf("Arjun")
        val surgeries = intent.getStringArrayExtra("SURGERIES") ?: arrayOf("Knee Surgery")
        val dates = intent.getStringArrayExtra("DATES") ?: arrayOf("2023-07-15")
        val diabetesFlags = intent.getBooleanArrayExtra("DIABETES_FLAGS") ?: booleanArrayOf(false)
        val foodPrefsList = intent.getSerializableExtra("FOOD_PREFS_LIST") as? Array<Array<String>> ?: arrayOf(arrayOf("Low Carb"))

        val loggedMeals = intent.getIntArrayExtra("LOGGED_MEALS") ?: intArrayOf(4)
        val totalMeals = intent.getIntArrayExtra("TOTAL_MEALS") ?: intArrayOf(5)

        val patientSection = findViewById<LinearLayout>(R.id.patient_details_section)
        patientSection.removeAllViews()

        // Dynamically create views for each patient
        for (i in patientNames.indices) {
            val tvPatientName = TextView(this).apply {
                text = "Patient Name: ${patientNames[i]}"
                textSize = 18f
            }
            val tvSurgery = TextView(this).apply {
                text = "Surgery: ${surgeries.getOrElse(i) { "N/A" }}"
                textSize = 18f
            }
            val tvDate = TextView(this).apply {
                text = "Date of Surgery: ${dates.getOrElse(i) { "N/A" }}"
                textSize = 18f
            }
            val tvDiabetes = TextView(this).apply {
                text = "Diabetes: ${if (diabetesFlags.getOrElse(i) { false }) "Yes" else "No"}"
                textSize = 18f
            }
            val tvFoodPrefs = TextView(this).apply {
                text = "Food Preferences: ${foodPrefsList.getOrElse(i) { arrayOf() }.joinToString(", ")}"
                textSize = 18f
            }
            val tvCompliance = TextView(this).apply {
                val compliance = if (totalMeals.getOrElse(i) { 5 } > 0)
                    (loggedMeals.getOrElse(i) { 0 } * 100) / totalMeals.getOrElse(i) { 5 }
                else 0
                text = "Compliance: $compliance%"
                textSize = 18f
            }

            // Optional: View Details Button
            val viewButton = Button(this).apply {
                text = "View Details"
                setOnClickListener {
                    val intent = Intent(this@Doctor1Activity, Doctor2Activity::class.java)
                    intent.putExtra("DOCTOR_NAME", doctorName)
                    intent.putExtra("PATIENT_NAME", patientNames[i])
                    intent.putExtra("SURGERY", surgeries.getOrElse(i) { "N/A" })
                    intent.putExtra("DATE", dates.getOrElse(i) { "N/A" })
                    intent.putExtra("DIABETES", diabetesFlags.getOrElse(i) { false })
                    intent.putExtra("FOOD_PREFS", foodPrefsList.getOrElse(i) { arrayOf() })
                    startActivity(intent)
                }
            }

            // Add all views to patient section
            patientSection.addView(tvPatientName)
            patientSection.addView(tvSurgery)
            patientSection.addView(tvDate)
            patientSection.addView(tvDiabetes)
            patientSection.addView(tvFoodPrefs)
            patientSection.addView(tvCompliance)
            patientSection.addView(viewButton)
        }
    }
}
