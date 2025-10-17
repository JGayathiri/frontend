package com.simats.surgi__diet

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat

class Mealb1Activity : AppCompatActivity() {

    private var selectedMealName: String? = null
    private var selectedMealDesc: String? = null
    private var selectedMealImg: Int = 0
    private var selectedCard: LinearLayout? = null
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mealb1)

        prefs = getSharedPreferences("MealPrefs", MODE_PRIVATE)

        val cardOatMeal = findViewById<LinearLayout>(R.id.cardOatMeal)
        val cardEgg = findViewById<LinearLayout>(R.id.cardEgg)
        val cardWheat = findViewById<LinearLayout>(R.id.cardWheat)
        val cardYogurt = findViewById<LinearLayout>(R.id.cardYogurt)

        cardOatMeal.setOnClickListener {
            setSelection(cardOatMeal, "Oatmeal with Fruits", "250 cal, 6g protein, 45g carbs, 4g fat", R.drawable.oatmeal)
        }
        cardEgg.setOnClickListener {
            setSelection(cardEgg, "Boiled Eggs", "140 cal, 12g protein, 2g carbs, 9g fat", R.drawable.boiledegg)
        }
        cardWheat.setOnClickListener {
            setSelection(cardWheat, "Whole Wheat Toast", "180 cal, 7g protein, 28g carbs, 3g fat", R.drawable.wheat)
        }
        cardYogurt.setOnClickListener {
            setSelection(cardYogurt, "Greek Yogurt", "160 cal, 15g protein, 10g carbs, 4g fat", R.drawable.greek)
        }

        findViewById<Button>(R.id.confirmBtn).setOnClickListener {
            if (selectedMealName == null) {
                Toast.makeText(this, "Please select a meal", Toast.LENGTH_SHORT).show()
            } else {
                // Save to SharedPreferences with key "Snack"
                prefs.edit().putString("Snack", selectedMealName).apply()
                Toast.makeText(this, "Snack swapped to $selectedMealName", Toast.LENGTH_SHORT).show()
                finish()  // Return to MealActivity
            }
        }

        findViewById<ImageView>(R.id.backBtn).setOnClickListener { finish() }
    }

    private fun setSelection(card: LinearLayout, name: String, desc: String, img: Int) {
        selectedCard?.background = ContextCompat.getDrawable(this, R.drawable.card_bg)
        card.background = ContextCompat.getDrawable(this, R.drawable.card_bg_selected)

        selectedCard = card
        selectedMealName = name
        selectedMealDesc = desc
        selectedMealImg = img
    }
}
