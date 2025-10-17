package com.simats.surgi__diet

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge layout
        enableEdgeToEdge()
        setContentView(R.layout.activity_home)

        // Apply padding for system bars (status + navigation)
        val mainLayout = findViewById<android.view.View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Retrieve user data from SharedPreferences ---
        val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
        val username = prefs.getString("USERNAME", "User")
        val surgery = prefs.getString("SURGERY", "Surgery")

        // --- Show greeting message ---
        val tvGreeting: TextView = findViewById(R.id.tvGreeting)
        tvGreeting.text = "Hi, $username!\nSurgery: $surgery"

        // Bottom Navigation setup
        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> true // Already on Home
                R.id.nav_meals -> {
                    openActivity(MealActivity::class.java)
                    true
                }
                R.id.nav_analysis -> {
                    openActivity(AnalysisActivity::class.java)
                    true
                }
                R.id.nav_profile -> {
                    openActivity(ProfileActivity::class.java)
                    true
                }
                else -> false
            }
        }

        // Click on meal image → go to MealActivity
        val imgMeal: ImageView = findViewById(R.id.imgMeal)
        imgMeal.setOnClickListener {
            openActivity(MealActivity::class.java)
        }
    }

    /**
     * Helper function to open an activity safely
     * Reuses the activity if already in back stack
     */
    private fun openActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT
        startActivity(intent)
    }
}
