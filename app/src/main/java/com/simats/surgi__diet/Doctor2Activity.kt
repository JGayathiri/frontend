package com.simats.surgi__diet

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Doctor2Activity : AppCompatActivity() {

    private val PICK_IMAGE_REQUEST_CODE = 100

    // To track which ImageView was clicked
    private var currentImageView: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_doctor2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Back button
        val backButton = findViewById<ImageView>(R.id.backButton)
        backButton.setOnClickListener {
            finish()
        }

        // Save Meal Plan button
        val saveButton = findViewById<Button>(R.id.saveMealPlan)
        saveButton.setOnClickListener {
            val intent = Intent(this, Doctor3Activity::class.java)
            startActivity(intent)
        }

        // Meal ImageViews
        val breakfastImage = findViewById<ImageView>(R.id.breakfast_image)
        val snack1Image = findViewById<ImageView>(R.id.snack1_image)
        val lunchImage = findViewById<ImageView>(R.id.lunch_image)
        val snack2Image = findViewById<ImageView>(R.id.snack2_image)

        val imageClickListener = { imageView: ImageView ->
            currentImageView = imageView
            pickImageFromGallery()
        }

        breakfastImage.setOnClickListener { imageClickListener(breakfastImage) }
        snack1Image.setOnClickListener { imageClickListener(snack1Image) }
        lunchImage.setOnClickListener { imageClickListener(lunchImage) }
        snack2Image.setOnClickListener { imageClickListener(snack2Image) }
    }

    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val imageUri: Uri? = data?.data
            if (imageUri != null && currentImageView != null) {
                currentImageView?.setImageURI(imageUri)
            } else {
                Toast.makeText(this, "Failed to select image", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
