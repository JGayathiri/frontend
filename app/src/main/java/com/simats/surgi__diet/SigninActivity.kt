package com.simats.surgi__diet

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.text.SimpleDateFormat
import java.util.*

class SigninActivity : AppCompatActivity() {

    private var selectedGender: Button? = null
    private var selectedSurgery: Button? = null
    private val selectedFood = mutableSetOf<Button>() // Multiple selections allowed

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_signin)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.mainScroll)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // --- Input Fields ---
        val usernameEditText: EditText = findViewById(R.id.usernameEditText)
        val passwordEditText: EditText = findViewById(R.id.passwordEditText)
        val emailEditText: EditText = findViewById(R.id.emailEditText)
        val heightEditText: EditText = findViewById(R.id.heightEditText)
        val weightEditText: EditText = findViewById(R.id.weightEditText)
        val ageEditText: EditText = findViewById(R.id.ageEditText)
        val otherSurgeryEditText: EditText = findViewById(R.id.otherSurgeryEditText)
        val dateEditText: EditText = findViewById(R.id.dateEditText)
        val diabetesSwitch: Switch = findViewById(R.id.diabetesSwitch)

        // --- Date Picker ---
        dateEditText.setOnClickListener {
            val calendar = Calendar.getInstance()
            DatePickerDialog(
                this,
                { _, y, m, d ->
                    dateEditText.setText(String.format("%04d/%02d/%02d", y, m + 1, d))
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            ).show()
        }

        // --- Gender Buttons ---
        val maleButton: Button = findViewById(R.id.maleButton)
        val femaleButton: Button = findViewById(R.id.femaleButton)
        val otherButton: Button = findViewById(R.id.otherButton)
        val genderButtons = listOf(maleButton, femaleButton, otherButton)
        genderButtons.forEach { button ->
            button.setOnClickListener {
                highlightSelected(button, genderButtons)
                selectedGender = button
            }
        }

        // --- Surgery Buttons ---
        val heartButton: Button = findViewById(R.id.heartButton)
        val kneeButton: Button = findViewById(R.id.kneeButton)
        val generalButton: Button = findViewById(R.id.generalButton)
        val surgeryButtons = listOf(heartButton, kneeButton, generalButton)
        surgeryButtons.forEach { button ->
            button.setOnClickListener {
                highlightSelected(button, surgeryButtons)
                selectedSurgery = button
            }
        }

        // --- Food Preference Buttons ---
        val milkFreeButton: Button = findViewById(R.id.milkFreeButton)
        val nutFreeButton: Button = findViewById(R.id.nutFreeButton)
        val vegetarianButton: Button = findViewById(R.id.vegetarianButton)
        val foodButtons = listOf(milkFreeButton, nutFreeButton, vegetarianButton)
        foodButtons.forEach { button ->
            button.setOnClickListener { toggleSelection(button) }
        }

        // --- Sign Up Button ---
        val signupButton: Button = findViewById(R.id.signupButton)
        signupButton.setOnClickListener {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()
            val email = emailEditText.text.toString().trim()
            val height = heightEditText.text.toString().trim()
            val weight = weightEditText.text.toString().trim()
            val age = ageEditText.text.toString().trim()
            val surgeryType = selectedSurgery?.text?.toString() ?: otherSurgeryEditText.text.toString().trim()
            val gender = selectedGender?.text?.toString()
            val foodPrefs = selectedFood.map { it.text }
            val date = dateEditText.text.toString().trim()
            val diabetes = diabetesSwitch.isChecked

            // --- VALIDATIONS ---

            // Username: not empty, min 3 chars
            if (username.isEmpty() || username.length < 3) {
                usernameEditText.error = "Username must be at least 3 characters"
                return@setOnClickListener
            }

            // Password: 8+ chars, 1 upper, 1 lower, 1 digit, 1 special
            val passwordRegex =
                Regex("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@\$!%*?&])[A-Za-z\\d@\$!%*?&]{8,}$")
            if (!password.matches(passwordRegex)) {
                passwordEditText.error =
                    "Password must have 8+ chars, 1 uppercase, 1 lowercase, 1 digit & 1 special char"
                return@setOnClickListener
            }

            // Email: valid format
            if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                emailEditText.error = "Enter valid email"
                return@setOnClickListener
            }

            // Height: numeric, 50–250 cm
            val heightVal = height.toIntOrNull()
            if (heightVal == null || heightVal < 50 || heightVal > 250) {
                heightEditText.error = "Height must be between 50 and 250 cm"
                return@setOnClickListener
            }

            // Weight: numeric, 10–300 kg
            val weightVal = weight.toIntOrNull()
            if (weightVal == null || weightVal < 10 || weightVal > 300) {
                weightEditText.error = "Weight must be between 10 and 300 kg"
                return@setOnClickListener
            }

            // Age: numeric, 1–120
            val ageVal = age.toIntOrNull()
            if (ageVal == null || ageVal < 1 || ageVal > 120) {
                ageEditText.error = "Age must be between 1 and 120"
                return@setOnClickListener
            }

            // Date of surgery: not empty and must be <= today
            if (date.isEmpty()) {
                dateEditText.error = "Select date"
                return@setOnClickListener
            } else {
                try {
                    val sdf = SimpleDateFormat("yyyy/MM/dd", Locale.US)
                    val selectedDate = sdf.parse(date)
                    val today = sdf.parse(sdf.format(Date()))
                    if (selectedDate != null && today != null && selectedDate.after(today)) {
                        dateEditText.error = "Date cannot be in the future"
                        return@setOnClickListener
                    }
                } catch (e: Exception) {
                    dateEditText.error = "Invalid date format"
                    return@setOnClickListener
                }
            }

            // Gender: must be selected
            if (gender.isNullOrEmpty()) {
                showToast("Please select gender")
                return@setOnClickListener
            }

            // Surgery type: must be selected or entered
            if (surgeryType.isEmpty()) {
                showToast("Select or enter surgery type")
                return@setOnClickListener
            }

            // --- Save all info in SharedPreferences ---
            val prefs = getSharedPreferences("UserPrefs", MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("USERNAME", username)
            editor.putString("SURGERY", surgeryType)
            editor.putString("EMAIL", email)
            editor.putString("GENDER", gender)
            editor.putString("HEIGHT", height)
            editor.putString("WEIGHT", weight)
            editor.putString("AGE", age)
            editor.putString("DATE", date)
            editor.putBoolean("DIABETES", diabetes)
            editor.putStringSet("FOOD_PREFS", foodPrefs.map { it.toString() }.toSet())
            editor.apply()

            // Navigate to HomeActivity
            startActivity(Intent(this, HomeActivity::class.java))
            finish()
        }
    }

    private fun highlightSelected(selected: Button, group: List<Button>) {
        group.forEach { button ->
            if (button == selected) {
                button.setBackgroundColor(Color.parseColor("#5A67F6"))
                button.setTextColor(Color.WHITE)
            } else {
                button.setBackgroundColor(Color.LTGRAY)
                button.setTextColor(Color.BLACK)
            }
        }
    }

    private fun toggleSelection(button: Button) {
        if (selectedFood.contains(button)) {
            selectedFood.remove(button)
            button.setBackgroundColor(Color.LTGRAY)
            button.setTextColor(Color.BLACK)
        } else {
            selectedFood.add(button)
            button.setBackgroundColor(Color.parseColor("#5A67F6"))
            button.setTextColor(Color.WHITE)
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
