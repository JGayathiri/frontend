package com.simats.surgi__diet

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Admin3Activity : AppCompatActivity() {

    private lateinit var logoutCard: CardView
    private lateinit var bottomNavigation: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin3)

        // Edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        logoutCard = findViewById(R.id.logoutCard)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        // Logout button click
        logoutCard.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            // Clear back stack so user cannot navigate back
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        // Bottom navigation click
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> {
                    // handle dashboard click if needed
                }
                R.id.navigation_doctors -> {
                    // handle doctors click if needed
                }
                R.id.navigation_patients -> {
                    // handle patients click if needed
                }
                R.id.navigation_settings -> {
                    // Already in settings
                }
            }
            true
        }
    }
}
