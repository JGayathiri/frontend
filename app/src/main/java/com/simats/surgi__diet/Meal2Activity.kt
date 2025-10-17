package com.simats.surgi__diet

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class Meal2Activity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences
    private var selectedMeal: String? = null
    private var selectedCard: LinearLayout? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal2)

        prefs = getSharedPreferences("MealPrefs", MODE_PRIVATE)

        // Back button
        findViewById<ImageView>(R.id.backBtn).setOnClickListener { finish() }

        // Meal cards
        setupMealCard(R.id.fruitSaladCard, "Fruit Salad")
        setupMealCard(R.id.nutYogurtCard, "Nut Yogurt")
        setupMealCard(R.id.bananaCard, "Banana with Peanut Butter")
        setupMealCard(R.id.sproutCard, "Sprouts Salad")

        // Confirm button
        findViewById<Button>(R.id.confirmBtn).setOnClickListener {
            if (selectedMeal != null) {
                prefs.edit().putString("Breakfast", selectedMeal).apply()
                Toast.makeText(this, "$selectedMeal selected ✅", Toast.LENGTH_SHORT).show()
                finish() // Back to MealActivity
            } else {
                Toast.makeText(this, "Please select a breakfast first!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupMealCard(cardId: Int, mealName: String) {
        val card = findViewById<LinearLayout>(cardId)
        card.setOnClickListener {
            // Unhighlight previous card
            selectedCard?.setBackgroundResource(R.drawable.card_bg)
            // Highlight selected
            card.setBackgroundResource(R.drawable.card_bg_selected)
            selectedCard = card
            selectedMeal = mealName
        }
    }
}
