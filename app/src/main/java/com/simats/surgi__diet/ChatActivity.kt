package com.simats.surgi__diet

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.Gravity
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

data class ChatMessage(val text: String, val senderRole: String)

class ChatActivity : AppCompatActivity() {

    private lateinit var messagesLayout: LinearLayout
    private lateinit var messagesScrollView: ScrollView
    private lateinit var messageInput: EditText
    private lateinit var sendButton: Button
    private lateinit var chatTitle: TextView
    private var role: String = "PATIENT" // Default role

    // Store chat messages
    private val chatMessages = mutableListOf<ChatMessage>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chat)

        messagesLayout = findViewById(R.id.messagesLayout)
        messagesScrollView = findViewById(R.id.messagesScrollView)
        messageInput = findViewById(R.id.messageInput)
        sendButton = findViewById(R.id.sendButton)
        chatTitle = findViewById(R.id.chatTitle)

        // Get role from intent
        role = intent.getStringExtra("ROLE") ?: "PATIENT"
        chatTitle.text = if (role == "PATIENT") "Patient Chat" else "Doctor Chat"

        // Load previous messages (if any)
        loadPreviousMessages()

        // Initial message for doctor view
        if (role == "DOCTOR" && chatMessages.isEmpty()) {
            addMessage("Hello! Patient queries will appear here.", "DOCTOR")
        }

        // Send message
        sendButton.setOnClickListener {
            val text = messageInput.text.toString().trim()
            if (text.isNotEmpty()) {
                sendMessage(text, role)
                messageInput.text.clear()
            }
        }
    }

    // Send a message
    private fun sendMessage(message: String, senderRole: String) {
        addMessage(message, senderRole)
        chatMessages.add(ChatMessage(message, senderRole))

        // Auto-replies
        if (senderRole == "PATIENT") {
            val reply = getAutoReply(message)
            addMessage(reply, "DOCTOR")
            chatMessages.add(ChatMessage(reply, "DOCTOR"))
        } else if (senderRole == "DOCTOR") {
            val reply = getPatientSimulatedReply(message)
            if (reply.isNotEmpty()) {
                addMessage(reply, "PATIENT")
                chatMessages.add(ChatMessage(reply, "PATIENT"))
            }
        }
    }

    // Load previous messages
    private fun loadPreviousMessages() {
        for (msg in chatMessages) {
            addMessage(msg.text, msg.senderRole)
        }
    }

    // Add a message bubble
    private fun addMessage(message: String, senderRole: String) {
        val messageTextView = TextView(this).apply {
            text = message
            setTextColor(Color.WHITE)
            setPadding(24, 24, 24, 24)
        }

        val params = LinearLayout.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        ).apply {
            setMargins(16, 8, 16, 8)
            gravity = if (senderRole == role) Gravity.END else Gravity.START
        }
        messageTextView.layoutParams = params

        // Bubble color
        messageTextView.setBackgroundColor(
            if (senderRole == role) Color.parseColor("#0A84FF") else Color.parseColor("#888888")
        )

        // Email tap
        if (message.contains("contact doctor", true) || message.contains("email", true)) {
            messageTextView.setOnClickListener {
                val intent = Intent(Intent.ACTION_SENDTO).apply {
                    data = Uri.parse("mailto:asjaykay6527@gmail.com")
                    putExtra(Intent.EXTRA_SUBJECT, "Post Surgery Diet Query")
                }
                startActivity(intent)
            }
        }

        // Phone number tap
        val phoneRegex = "\\b\\d{10}\\b".toRegex()
        val phoneMatch = phoneRegex.find(message)
        phoneMatch?.let { matchResult ->
            messageTextView.setOnClickListener {
                val dialIntent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:${matchResult.value}"))
                startActivity(dialIntent)
            }
        }

        messagesLayout.addView(messageTextView)
        messagesScrollView.post { messagesScrollView.fullScroll(ScrollView.FOCUS_DOWN) }
    }

    // Auto-reply for Patient messages (medical + casual)
    // Auto-reply for Patient messages (medical + casual)
    private fun getAutoReply(userMessage: String): String {
        val msg = userMessage.lowercase()
        return when {
            "hello" in msg || "hi" in msg -> "Hello! How are you today?"
            "how are you" in msg -> "I am doing well, thank you! How about you?"
            "good morning" in msg -> "Good morning! Hope you have a healthy day."
            "good night" in msg -> "Good night! Take care and rest well."
            "thank you" in msg || "thanks" in msg -> "You're welcome! Always here to help."
            "welcome" in msg -> "Thank you! How can I assist you today?"
            "meal" in msg -> "Please log your meal for today."
            "swapped meal" in msg -> "If you swapped meals, follow your plan closely. Contact doctor if unsure."
            "wrong food" in msg -> "Avoid foods not in your plan. Contact doctor if needed."
            "surgery" in msg -> "Remember to follow your post-surgery diet."
            "pain" in msg -> "If pain persists, contact your doctor immediately."
            "water" in msg -> "Drink at least 2 liters of water daily."
            "protein" in msg -> "Include sufficient protein in your meals."
            "vegetables" in msg -> "Eat fresh vegetables daily."
            "fruits" in msg -> "Include seasonal fruits in your diet."
            "supplement" in msg -> "Take prescribed supplements only."
            "exercise" in msg -> "Follow physiotherapy routine advised."
            "medication" in msg -> "Take your medication on time."
            "sleep" in msg -> "Ensure 7-8 hours of sleep daily."
            "weight" in msg -> "Avoid sudden weight gain; follow diet plan."
            "allergy" in msg -> "Avoid foods you are allergic to."
            "blood pressure" in msg -> "Monitor your blood pressure daily."
            "sugar" in msg -> "Limit sugar intake."
            "fat" in msg -> "Avoid high-fat foods."
            "fiber" in msg -> "Include fiber-rich foods."
            "swelling" in msg -> "Elevate the leg to reduce swelling."
            "infection" in msg -> "Keep wound clean to avoid infection."
            "nausea" in msg -> "Avoid foods that trigger nausea; contact doctor if persists."
            "constipation" in msg -> "Include fiber and water; if severe, contact doctor."
            "diarrhea" in msg -> "Stay hydrated; consult doctor if persistent."
            "cravings" in msg -> "Minor cravings are okay, avoid forbidden foods."
            "swallow" in msg -> "If difficulty swallowing, contact your doctor immediately."
            "doctor" in msg -> "You can contact the doctor at asjaykay6527@gmail.com"
            "urgent" in msg -> "For urgent concerns, call 1234567890."
            "diet" in msg -> "Follow the diet plan given by your doctor."
            "recovery" in msg -> "Recovery may take time; be patient."
            "appointment" in msg -> "Schedule your follow-up appointment with the doctor."
            else -> "I didn’t understand that. Please contact your doctor/admin for assistance."
        }
    }

    // Simulated Patient reply for Doctor messages
    private fun getPatientSimulatedReply(docMessage: String): String {
        val msg = docMessage.lowercase()
        return when {
            "hello" in msg || "hi" in msg -> "Hi! How are you?"
            "welcome" in msg -> "Thank you!"
            "meal" in msg -> "Sure, I will log my meal now."
            "exercise" in msg -> "Okay, I will follow the routine."
            "medication" in msg -> "I have taken my medicine."
            "water" in msg -> "I have drunk enough water today."
            "appointment" in msg -> "I will schedule my follow-up."
            "diet" in msg -> "I am following the diet as advised."
            else -> "I didn’t understand that. Please contact the admin for clarification."
        }
    }
}
