package com.simats.surgi__diet

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Meal3Activity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private var selectedMeal: String? = null
    private var selectedCard: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal3)

        prefs = getSharedPreferences("MealPrefs", MODE_PRIVATE)

        // Back button
        findViewById<ImageView>(R.id.backBtn).setOnClickListener { finish() }

        // Meal cards (names match MealActivity exactly)
        setupMealCard(R.id.cardCurdRice, "Curd Rice")
        setupMealCard(R.id.cardDal, "Dal with Rice")
        setupMealCard(R.id.cardFish, "Steamed Fish Curry")
        setupMealCard(R.id.cardSoup, "Lentil soup with Rice")
        setupMealCard(R.id.cardPulao, "Vegetable pulao")
        setupMealCard(R.id.cardPaneer, "Paneer curry with rice")

        // Confirm button
        findViewById<Button>(R.id.confirmBtn).setOnClickListener {
            if (selectedMeal != null) {
                prefs.edit().putString("Lunch", selectedMeal).apply()
                Toast.makeText(this, "$selectedMeal selected ✅", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Please select a lunch first!", Toast.LENGTH_SHORT).show()
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
