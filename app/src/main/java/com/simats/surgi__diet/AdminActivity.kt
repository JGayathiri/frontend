package com.simats.surgi__diet

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class AdminActivity : AppCompatActivity() {

    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin)

        // Edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize BottomNavigationView
        bottomNavigation = findViewById(R.id.bottomNavigation)

        // Bottom nav item selection
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    val intent = Intent(this, Admin1Activity::class.java)
                    startActivity(intent)
                }
                R.id.navigation_doctors -> {
                    val intent = Intent(this, Admin1Activity::class.java)
                    startActivity(intent)
                }
                R.id.navigation_patients -> {
                    val intent = Intent(this, Admin2Activity::class.java)
                    startActivity(intent)
                }
                R.id.navigation_settings -> {
                    val intent = Intent(this, Admin3Activity::class.java) // Settings page
                    startActivity(intent)
                }
            }
            true
        }
    }
}
