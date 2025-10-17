package com.simats.surgi__diet

import android.content.Intent
import android.graphics.Canvas
import android.graphics.Paint
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomnavigation.BottomNavigationView
import android.graphics.pdf.PdfDocument
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class ProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Back arrow
        findViewById<ImageView>(R.id.backArrow).setOnClickListener {
            finish()
        }

        // Export Report
        findViewById<LinearLayout>(R.id.exportReport).setOnClickListener {
            generatePdf()
        }

        // Support and Help
        // Support and Help
        findViewById<LinearLayout>(R.id.supportHelp).setOnClickListener {
            val intent = Intent(this, ChatActivity::class.java)
            intent.putExtra("ROLE", "PATIENT")  // Pass patient role
            startActivity(intent)
        }


        // Logout
        // Logout
        findViewById<LinearLayout>(R.id.logout).setOnClickListener {
            // Navigate to LoginActivity
            val intent = Intent(this, LoginActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish() // Optional, ensures ProfileActivity is removed from back stack
        }


        // Bottom Navigation
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        bottomNav.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> startActivity(Intent(this, HomeActivity::class.java)).let { true }
                R.id.nav_meals -> startActivity(Intent(this, MealActivity::class.java)).let { true }
                R.id.nav_analysis -> startActivity(Intent(this, AnalysisActivity::class.java)).let { true }
                R.id.nav_profile -> true
                else -> false
            }
        }
    }

    // Generate PDF Report with bar chart
    private fun generatePdf() {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas
        val paint = Paint()

        // Title
        paint.textSize = 22f
        paint.isFakeBoldText = true
        paint.color = android.graphics.Color.BLACK
        canvas.drawText("Patient Report", 180f, 50f, paint)

        // Patient Info
        paint.textSize = 16f
        paint.isFakeBoldText = true
        paint.color = android.graphics.Color.DKGRAY
        canvas.drawText("Patient Info", 50f, 100f, paint)

        paint.textSize = 14f
        paint.isFakeBoldText = false
        paint.color = android.graphics.Color.BLACK
        canvas.drawText("Name: Kavya Jay", 60f, 130f, paint)
        canvas.drawText("Email: kavya.shivam@gmail.com", 60f, 160f, paint)

        // Surgery Details
        paint.isFakeBoldText = true
        paint.color = android.graphics.Color.DKGRAY
        canvas.drawText("Surgery Details", 50f, 200f, paint)

        paint.textSize = 14f
        paint.isFakeBoldText = false
        paint.color = android.graphics.Color.BLACK
        canvas.drawText("Surgery: Heart Surgery", 60f, 230f, paint)
        canvas.drawText("Surgery Date: 15 May 2025", 60f, 260f, paint)

        // Allergies
        paint.isFakeBoldText = true
        paint.color = android.graphics.Color.DKGRAY
        canvas.drawText("Allergies / Preferences", 50f, 300f, paint)

        paint.textSize = 14f
        paint.isFakeBoldText = false
        paint.color = android.graphics.Color.BLACK
        canvas.drawText("Nut-free, Vegetarian", 60f, 330f, paint)

        // Daily Nutrition Bar Chart
        paint.textSize = 16f
        paint.isFakeBoldText = true
        paint.color = android.graphics.Color.DKGRAY
        canvas.drawText("Daily Nutrition", 50f, 370f, paint)

        val nutrients = listOf("Protein" to 28f / 70f, "Carbs" to 164f / 220f, "Fat" to 14f / 40f)
        var yPosition = 400f
        val barHeight = 30f
        val barMaxWidth = 400f
        val spacing = 20f

        for ((name, ratio) in nutrients) {
            paint.textSize = 14f
            paint.color = android.graphics.Color.BLACK
            canvas.drawText(name, 60f, yPosition + 20f, paint)

            paint.color = android.graphics.Color.LTGRAY
            canvas.drawRect(150f, yPosition, 150f + barMaxWidth, yPosition + barHeight, paint)

            val progressWidth = barMaxWidth * ratio
            paint.color = when (name) {
                "Protein" -> android.graphics.Color.parseColor("#4CAF50")
                "Carbs" -> android.graphics.Color.parseColor("#FF9800")
                "Fat" -> android.graphics.Color.parseColor("#E91E63")
                else -> android.graphics.Color.BLUE
            }
            canvas.drawRect(150f, yPosition, 150f + progressWidth, yPosition + barHeight, paint)

            paint.color = android.graphics.Color.BLACK
            paint.textSize = 12f
            val valueText = when (name) {
                "Protein" -> "28g / 70g"
                "Carbs" -> "164g / 220g"
                "Fat" -> "14g / 40g"
                else -> ""
            }
            canvas.drawText(valueText, 560f - paint.measureText(valueText), yPosition + 20f, paint)

            yPosition += barHeight + spacing
        }

        pdfDocument.finishPage(page)

        val filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
            .toString() + "/Patient_Report.pdf"
        val file = File(filePath)
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            Toast.makeText(this, "Report saved to Downloads", Toast.LENGTH_LONG).show()

            val intent = Intent(Intent.ACTION_VIEW)
            intent.setDataAndType(Uri.fromFile(file), "application/pdf")
            intent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to save report", Toast.LENGTH_SHORT).show()
        }
        pdfDocument.close()
    }
}
