package com.simats.surgi__diet

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView

class MealActivity : AppCompatActivity() {

    private lateinit var prefs: SharedPreferences

    // Map all meals to their detail activities
    private val mealNameDetailActivities = mapOf(
        // Breakfast
        "Vegetable oat porridge" to MealdetialsActivity::class.java,
        "Oatmeal with Fruits" to Mealdetialsbb1Activity::class.java,
        "Boiled Eggs" to Mealdetialsbb2Activity::class.java,
        "Whole Wheat Toast" to Mealdetialsbb3Activity::class.java,
        "Greek Yogurt" to Mealdetialsbb4Activity::class.java,
        "Fruit Salad" to Mealdetials5Activity::class.java,
        "Banana with Peanut Butter" to Mealdetialsb2Activity::class.java,
        "Nut Yogurt" to Mealdetialsb1Activity::class.java,
        "Sprouts Salad" to Mealdetialsb3Activity::class.java,

        // Snack
        "Fruit Smoothie" to Mealdetials1Activity::class.java,
        "Oatmeal with Fruits" to Mealdetialsbb1Activity::class.java,
        "Boiled Eggs" to Mealdetialsbb2Activity::class.java,
        "Whole Wheat Toast" to Mealdetialsbb3Activity::class.java,
        "Greek Yogurt" to Mealdetialsbb4Activity::class.java,

        // Lunch
        "Sambar with Veggies" to Mealdetials3Activity::class.java,
        "Curd Rice" to MealdetialslActivity::class.java,
        "Dal with Rice" to Mealdetialsl1Activity::class.java,
        "Steamed Fish Curry" to Mealdetialsl2Activity::class.java,
        "Lentil soup with Rice" to Mealdetialsl3Activity::class.java,
        "Vegetable pulao" to Mealdetialsl4Activity::class.java,
        "Paneer curry with rice" to Mealdetialsl5Activity::class.java,

        // Evening
        "Steamed Idly" to Mealdetials3Activity::class.java,
        "Ragi Malt" to Mealdetials3Activity::class.java,
        "Fruit Bowl" to Mealdetials4Activity::class.java,
        "Sprouts" to Mealdetials1Activity::class.java,
        "Veg Sandwich" to Mealdetials2Activity::class.java,

        // Dinner
        "Vegetable Stew" to Mealdetials4Activity::class.java,
        "Veg Soup & Bread" to Mealdetialsd1Activity::class.java,
        "moong dal kichadi" to Mealdetialsd2Activity::class.java,
        "Vegetable upma" to Mealdetialsd3Activity::class.java,
        "Idli with sambar" to Mealdetialsd4Activity::class.java,
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meal)

        prefs = getSharedPreferences("MealPrefs", MODE_PRIVATE)

        // Back button
        findViewById<ImageView>(R.id.ivBack).setOnClickListener { finish() }

        // Save button
        findViewById<Button>(R.id.saveMealPlanBtn).setOnClickListener {
            Toast.makeText(this, "Meal Plan Saved ✅", Toast.LENGTH_SHORT).show()
        }

        // Meal card clicks
        setupCardClick(R.id.cardBreakfast, "Breakfast")
        setupCardClick(R.id.cardSnack, "Snack")
        setupCardClick(R.id.cardLunch, "Lunch")
        setupCardClick(R.id.cardEvening, "Evening")
        setupCardClick(R.id.cardDinner, "Dinner")

        // ============ Logged & Skipped Buttons ============
        setupMealButtons("Breakfast", R.id.btnBreakfastLogged, R.id.btnBreakfastSkipped)
        setupMealButtons("Snack", R.id.btnSnackLogged, R.id.btnSnackSkipped)
        setupMealButtons("Lunch", R.id.btnLunchLogged, R.id.btnLunchSkipped)
        setupMealButtons("Evening", R.id.btnEveningLogged, R.id.btnEveningSkipped)
        setupMealButtons("Dinner", R.id.btnDinnerLogged, R.id.btnDinnerSkipped)

        // Swap buttons
        findViewById<ImageButton>(R.id.btnBreakfastSwap).setOnClickListener {
            startActivity(Intent(this, Meal2Activity::class.java))
        }
        findViewById<ImageButton>(R.id.btnSnackSwap).setOnClickListener {
            startActivity(Intent(this, Mealb1Activity::class.java))
        }
        findViewById<ImageButton>(R.id.btnLunchSwap).setOnClickListener {
            startActivity(Intent(this, Meal3Activity::class.java))
        }
        findViewById<ImageButton>(R.id.btnEveningSwap).setOnClickListener {
            startActivity(Intent(this, Meal4Activity::class.java))
        }
        findViewById<ImageButton>(R.id.btnDinnerSwap).setOnClickListener {
            startActivity(Intent(this, Meal5Activity::class.java))
        }

        // Bottom Navigation
        findViewById<BottomNavigationView>(R.id.bottomNavigation).apply {
            selectedItemId = R.id.nav_meals
            setOnItemSelectedListener { item ->
                when (item.itemId) {
                    R.id.nav_home -> { startActivity(Intent(context, HomeActivity::class.java)); true }
                    R.id.nav_meals -> true
                    R.id.nav_analysis -> { startActivity(Intent(context, AnalysisActivity::class.java)); true }
                    R.id.nav_profile -> { startActivity(Intent(context, ProfileActivity::class.java)); true }
                    else -> false
                }
            }
        }

        // Initialize cards
        updateAllCardsFromPrefs()
    }

    override fun onResume() {
        super.onResume()
        updateAllCardsFromPrefs()
    }

    // -------------------- Setup Card Click --------------------
    private fun setupCardClick(cardId: Int, mealKey: String) {
        findViewById<LinearLayout>(cardId).setOnClickListener {
            val mealName = prefs.getString(mealKey, getDefaultMealName(mealKey))
            val targetActivity = mealNameDetailActivities[mealName]
            if (targetActivity != null) {
                startActivity(Intent(this, targetActivity))
            } else {
                Toast.makeText(this, "Meal details not available for $mealName", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // -------------------- Setup Logged & Skipped --------------------
    private fun setupMealButtons(mealKey: String, loggedBtnId: Int, skippedBtnId: Int) {
        val loggedBtn = findViewById<Button>(loggedBtnId)
        val skippedBtn = findViewById<Button>(skippedBtnId)

        // Restore saved state
        val savedStatus = prefs.getString("${mealKey}_Status", "")
        updateMealButtonUI(savedStatus, loggedBtn, skippedBtn)

        loggedBtn.setOnClickListener {
            prefs.edit().putString("${mealKey}_Status", "Logged").apply()
            updateMealButtonUI("Logged", loggedBtn, skippedBtn)
            Toast.makeText(this, "$mealKey Logged ✅", Toast.LENGTH_SHORT).show()
        }

        skippedBtn.setOnClickListener {
            prefs.edit().putString("${mealKey}_Status", "Skipped").apply()
            updateMealButtonUI("Skipped", loggedBtn, skippedBtn)
            Toast.makeText(this, "$mealKey Skipped ❌", Toast.LENGTH_SHORT).show()
        }
    }

    private fun updateMealButtonUI(status: String?, loggedBtn: Button, skippedBtn: Button) {
        when (status) {
            "Logged" -> {
                loggedBtn.isEnabled = false
                skippedBtn.isEnabled = true
                loggedBtn.setBackgroundColor(getColor(android.R.color.holo_green_light))
                skippedBtn.setBackgroundColor(getColor(android.R.color.darker_gray))
            }
            "Skipped" -> {
                loggedBtn.isEnabled = true
                skippedBtn.isEnabled = false
                loggedBtn.setBackgroundColor(getColor(android.R.color.darker_gray))
                skippedBtn.setBackgroundColor(getColor(android.R.color.holo_red_light))
            }
            else -> {
                loggedBtn.isEnabled = true
                skippedBtn.isEnabled = true
                loggedBtn.setBackgroundColor(getColor(android.R.color.holo_blue_light))
                skippedBtn.setBackgroundColor(getColor(android.R.color.holo_blue_light))
            }
        }
    }

    // -------------------- Update Cards --------------------
    private fun updateAllCardsFromPrefs() {
        updateMealCard("Breakfast", R.id.imgBreakfast, R.id.tvBreakfastTitle, R.id.tvBreakfastDesc)
        updateMealCard("Snack", R.id.imgSnack, R.id.tvSnackTitle, R.id.tvSnackDesc)
        updateMealCard("Lunch", R.id.imgLunch, R.id.tvLunchTitle, R.id.tvLunchDesc)
        updateMealCard("Evening", R.id.imgEvening, R.id.tvEveningTitle, R.id.tvEveningDesc)
        updateMealCard("Dinner", R.id.imgDinner, R.id.tvDinnerTitle, R.id.tvDinnerDesc)
    }

    private fun updateMealCard(mealKey: String, imgId: Int, titleId: Int, descId: Int) {
        val mealName = prefs.getString(mealKey, getDefaultMealName(mealKey)) ?: getDefaultMealName(mealKey)
        findViewById<ImageView>(imgId).setImageResource(getMealImg(mealName))
        findViewById<TextView>(titleId).text = mealName
        findViewById<TextView>(descId).text = getMealDesc(mealName)
    }

    // -------------------- Meal Info --------------------
    private fun getMealImg(name: String) = when (name) {
        // Breakfast
        "Vegetable oat porridge" -> R.drawable.oat
        "Oatmeal with Fruits" -> R.drawable.oatmeal
        "Boiled Eggs" -> R.drawable.boiledegg
        "Whole Wheat Toast" -> R.drawable.wheat
        "Greek Yogurt" -> R.drawable.greek
        "Fruit Salad" -> R.drawable.fruitsalad
        "Banana with Peanut Butter" -> R.drawable.banana
        "Nut Yogurt" -> R.drawable.nut
        "Sprouts Salad" -> R.drawable.sprout
        // Snack
        "Fruit Smoothie" -> R.drawable.smoothie
        "Ragi Malt" -> R.drawable.ragi
        "Fruit Bowl" -> R.drawable.fruitsalad
        // Lunch
        "Sambar with Veggies" -> R.drawable.sambar
        "Curd Rice" -> R.drawable.curdrice
        "Dal with Rice" -> R.drawable.dal
        "Steamed Fish Curry" -> R.drawable.fish
        "Lentil soup with Rice" -> R.drawable.lentil
        "Vegetable pulao" -> R.drawable.pulao
        "Paneer curry with rice" -> R.drawable.paneer
        // Evening
        "Steamed Idly" -> R.drawable.idly
        "Sprouts" -> R.drawable.sprout
        "Veg Sandwich" -> R.drawable.sandwich
        // Dinner
        "Vegetable Stew" -> R.drawable.stew
        "Veg Soup & Bread" -> R.drawable.soup
        "Vegetable upma" -> R.drawable.upma
        "Idli with sambar" -> R.drawable.idli
        else -> R.drawable.ic_launcher_background
    }

    private fun getMealDesc(name: String) = when (name) {
        "Vegetable oat porridge" -> "Rich in fibre and protein"
        "Oatmeal with Fruits" -> "Healthy & filling"
        "Boiled Eggs" -> "High protein"
        "Whole Wheat Toast" -> "Good carbs"
        "Greek Yogurt" -> "Protein-rich snack"
        "Fruit Salad" -> "Light & healthy"
        "Banana with Peanut Butter" -> "Energy-packed"
        "Nut Yogurt" -> "Protein-rich snack"
        "Sprouts Salad" -> "Light & healthy"
        "Fruit Smoothie" -> "Boosts immunity"
        "Ragi Malt" -> "Healthy drink"
        "Fruit Bowl" -> "Light & nutritious"
        "Sambar with Veggies" -> "Protein-packed meal"
        "Curd Rice" -> "Light and healthy"
        "Dal with Rice" -> "Fibre & protein"
        "Steamed Fish Curry" -> "High protein meal"
        "Lentil soup with Rice" -> "Healthy & filling"
        "Vegetable pulao" -> "Fibre & vitamins"
        "Paneer curry with rice" -> "Protein-rich lunch"
        "Steamed Idly" -> "Light and healthy"
        "Sprouts" -> "Healthy evening snack"
        "Veg Sandwich" -> "Tasty & filling"
        "Vegetable Stew" -> "Full of vitamins"
        "moong dal kichadi" -> "Light dinner menu"
        "Veg Soup & Bread" -> "Light dinner option"
        "Vegetable upma" -> "Healthy dinner"
        "Idli with sambar" -> "Light and nutritious"
        else -> ""
    }

    private fun getDefaultMealName(mealKey: String) = when (mealKey) {
        "Breakfast" -> "Vegetable oat porridge"
        "Snack" -> "Fruit Smoothie"
        "Lunch" -> "Sambar with Veggies"
        "Evening" -> "Steamed Idly"
        "Dinner" -> "Vegetable Stew"
        else -> ""
    }
}
