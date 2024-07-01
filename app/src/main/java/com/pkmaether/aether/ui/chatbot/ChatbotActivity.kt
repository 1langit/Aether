package com.pkmaether.aether.ui.chatbot

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.ai.client.generativeai.Chat
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import com.pkmaether.aether.BuildConfig
import com.pkmaether.aether.R
import com.pkmaether.aether.data.models.ChatMessage
import com.pkmaether.aether.data.models.Participant
import com.pkmaether.aether.databinding.ActivityChatbotBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatbotActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChatbotBinding
    private lateinit var chat: Chat
    private lateinit var chatViewModel: ChatViewModel
    private val chatAdapter = ChatAdapter(this@ChatbotActivity)

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
        chatViewModel = ViewModelProvider(this, ViewModelProvider.AndroidViewModelFactory.getInstance(application))[ChatViewModel::class.java]
        initiateChat()

        with(binding) {
            rvChat.apply {
                adapter = chatAdapter
                layoutManager = LinearLayoutManager(this@ChatbotActivity)
            }

            inputMessage.setEndIconOnClickListener {
                val message = edtMessage.text.toString()
                if (message.isNotBlank() && !chatViewModel.isLoading.value!!) {
                    sendMessage(message)
                    edtMessage.setText("")
                }
            }

            chatViewModel.messages.observe(this@ChatbotActivity) { messages ->
                chatAdapter.setMessages(messages)
                if (messages.isNotEmpty()) rvChat.scrollToPosition(messages.size - 1)
            }

            chatViewModel.isLoading.observe(this@ChatbotActivity) { isLoading ->
                progressResponse.visibility = if (isLoading) View.VISIBLE else View.GONE
            }
        }
    }

    private fun initiateChat() {
        val generativeModel = GenerativeModel(
            apiKey = BuildConfig.API_KEY,
            modelName = "gemini-1.5-flash",
        )

        chat = generativeModel.startChat(
            history = listOf(
                content(role = "user") { text(getString(R.string.chat_prompt)) },
                content(role = "model") { text(getString(R.string.first_chat)) }
            )
        )
        chatViewModel.addMessage(ChatMessage(id = "0", participant = Participant.MODEL, text = getString(R.string.first_chat)))
    }

    private fun sendMessage(message: String) {
        chatViewModel.addMessage(ChatMessage(participant = Participant.USER, text = message))
        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            val response = chat.sendMessage(message)
            response.text?.let { modelResponse ->
                withContext(Dispatchers.Main) {
                    chatViewModel.addMessage(ChatMessage(participant = Participant.MODEL, text = modelResponse))
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.chatbot_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.clear -> {
                chatAdapter.resetMessages()
                chatViewModel.clearChatHistory()
                chatViewModel.addMessage(ChatMessage(id = "0", participant = Participant.MODEL, text = getString(R.string.first_chat)))
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}