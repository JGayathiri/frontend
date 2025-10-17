package com.simats.surgi__diet

import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Mealdetialsl3Activity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_mealdetialsl5)

        // Handle system insets (status/navigation bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // ✅ Back button
        findViewById<ImageView>(R.id.ivBack).setOnClickListener {
            finish()
        }

        // ✅ Swap Meal button
        findViewById<Button>(R.id.btnSwapMeal).setOnClickListener {
            Toast.makeText(this, "Swap Meal clicked 🔄", Toast.LENGTH_SHORT).show()
        }

        // ✅ Log Meal button
        findViewById<Button>(R.id.btnLogMeal).setOnClickListener {
            Toast.makeText(this, "Meal logged successfully ✅", Toast.LENGTH_SHORT).show()
        }
    }
}
