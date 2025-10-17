package com.simats.surgi__diet

import android.Manifest
import android.app.*
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.text.InputType
import android.widget.*
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.*

class DoctorprofileActivity : AppCompatActivity() {

    private lateinit var editProfileCard: CardView
    private lateinit var logoutCard: CardView
    private lateinit var notificationCard: CardView
    private lateinit var supportChatCard: CardView
    private lateinit var notificationPrefText: TextView

    // Doctor info TextViews
    private lateinit var doctorName: TextView
    private lateinit var doctorSpecialty: TextView
    private lateinit var doctorHospital: TextView
    private lateinit var doctorLicense: TextView
    private lateinit var doctorExperience: TextView

    // Launcher for notification permission
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
            if (isGranted) {
                Toast.makeText(this, "Notifications enabled", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Notifications denied", Toast.LENGTH_SHORT).show()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctorprofile)

        // Edge-to-edge padding
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize Views
        editProfileCard = findViewById(R.id.editProfileSection)
        logoutCard = findViewById(R.id.logoutSection)
        notificationCard = findViewById(R.id.notificationSection)
        supportChatCard = findViewById(R.id.supportChatSection)
        notificationPrefText = findViewById(R.id.notificationPref)

        doctorName = findViewById(R.id.doctorName)
        doctorSpecialty = findViewById(R.id.doctorSpecialty)
        doctorHospital = findViewById(R.id.doctorHospital)
        doctorLicense = findViewById(R.id.doctorLicense)
        doctorExperience = findViewById(R.id.doctorExperience)

        // Ask for notification permission
        askNotificationPermission()

        // Edit Profile
        editProfileCard.setOnClickListener { showEditProfileDialog() }

        // Logout
        logoutCard.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            Toast.makeText(this, "Logged out successfully", Toast.LENGTH_SHORT).show()
        }

        // Support Chat navigation
        // Support Chat navigation
        supportChatCard.setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("ROLE", "DOCTOR")  // Pass doctor role
            startActivity(intent)
        }

        // Create notification channel
        createNotificationChannel()

        // Notification Time Picker
        notificationCard.setOnClickListener {
            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(this, { _, selectedHour, selectedMinute ->
                val formattedTime = String.format("%02d:%02d", selectedHour, selectedMinute)
                notificationPrefText.text = "Notifications: ON ($formattedTime)"
                Toast.makeText(this, "Notification set for $formattedTime", Toast.LENGTH_SHORT).show()

                // Schedule notification
                scheduleNotification(selectedHour, selectedMinute)
            }, hour, minute, false)

            timePicker.show()
        }
    }

    private fun scheduleNotification(hour: Int, minute: Int) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1) // Schedule for tomorrow if time has passed
            }
        }

        val intent = Intent(this, NotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            this,
            0,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            pendingIntent
        )
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "meal_channel",
                "Meal Notifications",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.description = "Reminders for meals"
            val manager = getSystemService(NotificationManager::class.java)
            manager.createNotificationChannel(channel)
        }
    }

    private fun askNotificationPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    // Edit Profile Dialog Function
    private fun showEditProfileDialog() {
        val dialogLayout = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            setPadding(30, 20, 30, 20)
        }

        val nameInput = EditText(this).apply { hint = "Name"; setText(doctorName.text) }
        val specialtyInput = EditText(this).apply { hint = "Specialty"; setText(doctorSpecialty.text) }
        val hospitalInput = EditText(this).apply { hint = "Hospital"; setText(doctorHospital.text) }
        val licenseInput = EditText(this).apply { hint = "License"; setText(doctorLicense.text) }
        val experienceInput = EditText(this).apply {
            hint = "Experience"
            inputType = InputType.TYPE_CLASS_NUMBER
            setText(doctorExperience.text.toString().replace("[^0-9+]".toRegex(), ""))
        }

        dialogLayout.addView(nameInput)
        dialogLayout.addView(specialtyInput)
        dialogLayout.addView(hospitalInput)
        dialogLayout.addView(licenseInput)
        dialogLayout.addView(experienceInput)

        AlertDialog.Builder(this)
            .setTitle("Edit Profile")
            .setView(dialogLayout)
            .setPositiveButton("Save") { _, _ ->
                doctorName.text = nameInput.text.toString()
                doctorSpecialty.text = specialtyInput.text.toString()
                doctorHospital.text = hospitalInput.text.toString()
                doctorLicense.text = licenseInput.text.toString()
                doctorExperience.text = experienceInput.text.toString() + "+ Years of Experience"
                Toast.makeText(this, "Profile updated", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Inner class for notification receiver
    class NotificationReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent?) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) return

            val builder = NotificationCompat.Builder(context, "meal_channel")
                .setSmallIcon(R.drawable.notification) // Replace with your app icon
                .setContentTitle("Meal Reminder")
                .setContentText("It’s time for your scheduled meal log.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(context)) {
                notify(1001, builder.build())
            }
        }
    }
}
