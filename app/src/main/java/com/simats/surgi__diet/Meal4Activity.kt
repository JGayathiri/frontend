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

class Meal4Activity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private var selectedCard: LinearLayout? = null
    private var selectedMeal: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal4)

        prefs = getSharedPreferences("MealPrefs", MODE_PRIVATE)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Back button
        findViewById<ImageView>(R.id.backBtn).setOnClickListener { finish() }

        // Meal cards (names match MealActivity exactly)
        setupMealCard(R.id.cardSprouts, "Sprouts")
        setupMealCard(R.id.cardSandwich, "Veg Sandwich")
        setupMealCard(R.id.cardRagiMalt, "Ragi Malt")
        setupMealCard(R.id.cardFruitBowl, "Fruit Bowl")

        // Confirm button
        findViewById<Button>(R.id.confirmBtn).setOnClickListener {
            if (selectedMeal != null) {
                prefs.edit().putString("Evening", selectedMeal).apply()
                Toast.makeText(this, "$selectedMeal selected ✅", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please select an evening snack first!", Toast.LENGTH_SHORT).show()
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
