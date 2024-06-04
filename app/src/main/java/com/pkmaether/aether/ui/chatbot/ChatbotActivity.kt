package com.pkmaether.aether.ui.chatbot

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.pkmaether.aether.BuildConfig
import com.pkmaether.aether.R
import com.pkmaether.aether.databinding.ActivityChatbotBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding
    private lateinit var chat: Chat

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityChatbotBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top + 60, systemBars.right, systemBars.bottom)
            insets
        }

        title = "Chatbot"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initiateChat()

        with(binding) {
            inputMessage.setEndIconOnClickListener {
                sendMessage(edtMessage.text.toString())
            }
        }
    }

    private fun initiateChat() {
        val generativeModel = GenerativeModel(
            apiKey = BuildConfig.API_KEY,
            modelName = "gemini-1.5-pro",
            systemInstruction = content(role = "user") { text(getString(R.string.chat_prompt)) }
        )

        chat = generativeModel.startChat(
            history = listOf(
                content(role = "model") { text(getString(R.string.first_chat)) }
            )
        )
    }

    private fun sendMessage(message: String) {
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val response = chat.sendMessage(message)
            response.text?.let { modelResponse ->
                // save to database
                // listen changes
                // update UI
            }
        }
    }
}