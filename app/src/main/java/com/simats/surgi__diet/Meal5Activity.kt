package com.simats.surgi__diet

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Meal5Activity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private var selectedCard: LinearLayout? = null
    private var selectedMeal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal5)

        prefs = getSharedPreferences("MealPrefs", MODE_PRIVATE)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Back button
        findViewById<ImageView>(R.id.backBtn).setOnClickListener { finish() }

        // Meal cards (names match MealActivity exactly)
        setupMealCard(R.id.cardSoup, "Veg Soup & Bread")
        setupMealCard(R.id.cardKhichdi, "moong dal kichadi")
        setupMealCard(R.id.cardUpma, "Vegetable upma")
        setupMealCard(R.id.cardIdli, "Idli with sambar")

        // Confirm button
        findViewById<Button>(R.id.confirmBtn).setOnClickListener {
            if (selectedMeal != null) {
                prefs.edit().putString("Dinner", selectedMeal).apply()
                Toast.makeText(this, "$selectedMeal selected ✅", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please select a dinner first!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupMealCard(cardId: Int, mealName: String) {
        val card = findViewById<LinearLayout>(cardId)
        card.setOnClickListener {
            selectedCard?.setBackgroundResource(R.drawable.card_bg)
            card.setBackgroundResource(R.drawable.card_bg_selected)
            selectedCard = card
            selectedMeal = mealName
        }
    }
}
