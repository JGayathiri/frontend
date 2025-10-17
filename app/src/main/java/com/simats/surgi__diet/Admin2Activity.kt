package com.simats.surgi__diet

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView

class Admin2Activity : AppCompatActivity() {

    private lateinit var backArrow: ImageView
    private lateinit var spinnerDoctor: Spinner
    private lateinit var saveAssignmentBtn: Button
    private lateinit var bottomNavigation: BottomNavigationView

    private lateinit var patientName: TextView
    private lateinit var patientCondition: TextView
    private lateinit var patientSurgeryDate: TextView

    private lateinit var patientCard: LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_admin2)

        // Edge-to-edge support
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        backArrow = findViewById(R.id.backArrow)
        spinnerDoctor = findViewById(R.id.spinnerDoctor)
        saveAssignmentBtn = findViewById(R.id.saveAssignmentBtn)
        bottomNavigation = findViewById(R.id.bottomNavigation)

        patientName = findViewById(R.id.patientName)
        patientCondition = findViewById(R.id.patientCondition)
        patientSurgeryDate = findViewById(R.id.patientSurgeryDate)
        patientCard = findViewById(R.id.patientCard)

        // Back arrow click
        backArrow.setOnClickListener { finish() }

        // Setup spinner with doctor names
        val doctorList = listOf("Dr. Shivam", "Dr. Kavi", "Dr. Arjun")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, doctorList)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinnerDoctor.adapter = adapter

        spinnerDoctor.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedDoctor = doctorList[position]
                Toast.makeText(this@Admin2Activity, "Selected: $selectedDoctor", Toast.LENGTH_SHORT).show()
            }
            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        // Save assignment button click
        saveAssignmentBtn.setOnClickListener {
            val selectedDoctor = spinnerDoctor.selectedItem.toString()
            patientCondition.text = "Assigned to $selectedDoctor"
            Toast.makeText(this, "Assigned ${patientName.text} to $selectedDoctor", Toast.LENGTH_LONG).show()
            saveAssignmentBtn.isEnabled = false
            saveAssignmentBtn.text = "Assigned"
        }

        // Bottom navigation click
        bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navigation_dashboard -> startActivity(Intent(this, Admin1Activity::class.java))
                R.id.navigation_doctors -> startActivity(Intent(this, Admin1Activity::class.java))
                R.id.navigation_patients -> startActivity(Intent(this, Admin2Activity::class.java))
                R.id.navigation_settings -> startActivity(Intent(this, Admin3Activity::class.java))
            }
            true
        }
    }
}
