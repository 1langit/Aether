package com.pkmaether.aether.ui.chatbot

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pkmaether.aether.data.models.ChatMessage
import com.pkmaether.aether.data.models.Participant
import com.pkmaether.aether.data.repositories.ChatLocalRepository

class ChatViewModel(application: Application) : AndroidViewModel(application) {

    private val _messages = MutableLiveData<List<ChatMessage>>()
    val messages: LiveData<List<ChatMessage>> = _messages

    private val _isLoading = MutableLiveData(false)
    val isLoading: LiveData<Boolean> = _isLoading

    private val chatLocalRepository = ChatLocalRepository(application)

    init {
        getChatHistory()
    }

    fun addMessage(message: ChatMessage) {
        _isLoading.value = message.participant == Participant.USER
        if (chatLocalRepository.insertMessage(message)) {
            _messages.value = _messages.value.orEmpty() + message
        }
    }

    private fun getChatHistory() {
        _messages.value = chatLocalRepository.getChatHistory()
    }

    fun clearChatHistory() {
        chatLocalRepository.clearChatHistory()
        _messages.value = emptyList()
    }
}