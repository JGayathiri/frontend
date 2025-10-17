package com.simats.surgi__diet

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class AnalysisActivity : AppCompatActivity() {

    private lateinit var progressCircular: ProgressBar
    private lateinit var tvCalories: TextView
    private val totalCalories = 1700
    private val currentCalories = 900

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_analysis)

        // Initialize Views
        progressCircular = findViewById(R.id.progressCircular)
        tvCalories = findViewById(R.id.tvCalories)

        // Set Calories Progress
        progressCircular.max = totalCalories
        progressCircular.progress = currentCalories
        tvCalories.text = "$currentCalories / $totalCalories kcal"

        // Set Article Buttons
        setupArticleButtons()

        // Bottom Navigation
        setupBottomNavigation()
    }

    private fun setupArticleButtons() {
        val btnArticle1: Button = findViewById(R.id.btnArticle1)
        val btnArticle2: Button = findViewById(R.id.btnArticle2)
        val btnArticle3: Button = findViewById(R.id.btnArticle3)

        btnArticle1.setOnClickListener {
            openPdf("https://www.who.int/publications/i/item/9789241599979")
        }

        btnArticle2.setOnClickListener {
            openPdf("https://www.ncbi.nlm.nih.gov/pmc/articles/PMC5547363/")
        }

        btnArticle3.setOnClickListener {
            openPdf("https://www.healthline.com/nutrition/protein-for-healing")
        }
    }

    private fun setupBottomNavigation() {
        val bottomNav: BottomNavigationView = findViewById(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    startActivity(Intent(this, HomeActivity::class.java))
                    true
                }
                R.id.nav_meals -> {
                    startActivity(Intent(this, MealActivity::class.java))
                    true
                }
                R.id.nav_analysis -> true // current page
                R.id.nav_profile -> {
                    startActivity(Intent(this, ProfileActivity::class.java))
                    true
                }
                else -> false
            }
        }
    }

    // Function to open PDFs/articles in browser
    private fun openPdf(url: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(this, "Unable to open article", Toast.LENGTH_SHORT).show()
        }
    }
}
