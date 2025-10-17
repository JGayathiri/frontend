package com.simats.surgi__diet

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Doctor3Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctor3)

        // Handle system bars padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Back arrow
        findViewById<ImageView>(R.id.backArrow).setOnClickListener { finish() }

        // Doctor Profile button
        findViewById<Button>(R.id.btnDoctorProfile).setOnClickListener {
            startActivity(Intent(this, DoctorprofileActivity::class.java))
        }

        // ----------- Patient Info from UserPrefs ----------
        val userPrefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = userPrefs.getString("USERNAME", "Patient Name")
        val date = userPrefs.getString("DATE", "N/A")

        findViewById<TextView>(R.id.subtitle).text = "Patient: $username | Date: $date"
        findViewById<TextView>(R.id.dailyMealLogTitle).text = "Daily Meal Log for $date"

        // ----------- Meal Logs from MealPrefs ----------
        val mealPrefs = getSharedPreferences("MealPrefs", MODE_PRIVATE)
        val meals = listOf(
            MealData("Breakfast", "mealName", "mealTime", "mealStatus", null),
            MealData("Snack", "morningSnackName", "morningSnackTime", "morningSnackStatus", null),
            MealData("Lunch", "lunchName", "lunchTime", "lunchStatus", "lunchSwapInfo"),
            MealData("Evening", "eveningSnackName", "eveningSnackTime", "eveningSnackStatus", null),
            MealData("Dinner", "dinnerName", "dinnerTime", "dinnerStatus", null)
        )

        meals.forEach { meal -> updateMealCard(meal, mealPrefs) }

        // ----------- Compliance ----------
        calculateCompliance(meals, mealPrefs)
    }

    private fun updateMealCard(meal: MealData, prefs: android.content.SharedPreferences) {
        val nameView = findViewById<TextView>(resources.getIdentifier(meal.nameId, "id", packageName))
        val timeView = findViewById<TextView>(resources.getIdentifier(meal.timeId, "id", packageName))
        val statusView = findViewById<TextView>(resources.getIdentifier(meal.statusId, "id", packageName))
        val swapView = meal.swapId?.let { findViewById<TextView>(resources.getIdentifier(it, "id", packageName)) }

        val mealName = prefs.getString("${meal.key}_Name", meal.key)
        val mealTime = prefs.getString("${meal.key}_Time", defaultMealTime(meal.key))
        nameView.text = mealName
        timeView.text = mealTime

        when (prefs.getString("${meal.key}_Status", "Pending")) {
            "Logged" -> {
                statusView.text = "Logged"
                statusView.setTextColor(getColor(android.R.color.holo_green_dark))
            }
            "Skipped" -> {
                statusView.text = "Skipped"
                statusView.setTextColor(getColor(android.R.color.holo_red_dark))
            }
            "Swapped" -> {
                statusView.text = "Swapped"
                statusView.setTextColor(getColor(android.R.color.holo_orange_dark))
            }
            else -> {
                statusView.text = "Pending"
                statusView.setTextColor(getColor(android.R.color.darker_gray))
            }
        }

        swapView?.let {
            val swapInfo = prefs.getString("${meal.key}_SwapInfo", "")
            if (!swapInfo.isNullOrEmpty()) {
                it.text = swapInfo
                it.visibility = android.view.View.VISIBLE
            } else {
                it.visibility = android.view.View.GONE
            }
        }
    }

    private fun calculateCompliance(meals: List<MealData>, prefs: android.content.SharedPreferences) {
        var logged = 0
        var skipped = 0
        var swapped = 0

        meals.forEach { meal ->
            when (prefs.getString("${meal.key}_Status", "")) {
                "Logged" -> logged++
                "Skipped" -> skipped++
                "Swapped" -> swapped++
            }
        }

        val total = meals.size
        val compliance = if (total > 0) (logged * 100) / total else 0
        findViewById<TextView>(R.id.compliancePercentage).text = "$compliance%"
    }

    private fun defaultMealTime(mealKey: String) = when (mealKey) {
        "Breakfast" -> "8:00 AM"
        "Snack" -> "10:30 AM"
        "Lunch" -> "1:00 PM"
        "Evening" -> "4:00 PM"
        "Dinner" -> "7:30 PM"
        else -> ""
    }

    data class MealData(
        val key: String,
        val nameId: String,
        val timeId: String,
        val statusId: String,
        val swapId: String?
    )
}
