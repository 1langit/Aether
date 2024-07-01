package com.pkmaether.aether.ui.chatbot

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.google.firebase.Timestamp
import com.pkmaether.aether.data.models.ChatMessage
import com.pkmaether.aether.data.models.Participant
import com.pkmaether.aether.databinding.ItemChatLeftBinding
import com.pkmaether.aether.databinding.ItemChatRightBinding
import java.text.SimpleDateFormat
import java.util.Locale
import io.noties.markwon.Markwon

class ChatAdapter(private val context: Context) : RecyclerView.Adapter<ViewHolder>() {

    private val chatList = ArrayList<ChatMessage>()

    companion object {
        const val RIGHT_VIEW = 1
        const val LEFT_VIEW = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            LEFT_VIEW -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemChatLeftBinding.inflate(inflater, parent, false)
                LeftViewHolder(binding)
            }
            else -> {
                val inflater = LayoutInflater.from(parent.context)
                val binding = ItemChatRightBinding.inflate(inflater, parent, false)
                RightViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int = chatList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (chatList[position].participant == Participant.USER)
            (holder as RightViewHolder).bind(chatList[position])
        else
            (holder as LeftViewHolder).bind(chatList[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (chatList[position].participant == Participant.USER) RIGHT_VIEW else LEFT_VIEW
    }

    inner class LeftViewHolder(private val binding: ItemChatLeftBinding) : ViewHolder(binding.root) {
        fun bind(chat: ChatMessage) {
            binding.apply {
                Markwon.create(context).setMarkdown(txtMessage, chat.text)
                txtTime.text = timestampToTime(chat.timestamp)
            }
        }
    }

    inner class RightViewHolder(private val binding: ItemChatRightBinding) : ViewHolder(binding.root) {
        fun bind(chat: ChatMessage) {
            binding.apply {
                txtMessage.text = chat.text
                txtTime.text = timestampToTime(chat.timestamp)
            }
        }
    }

    fun setMessages(messages: List<ChatMessage>) {
        chatList.clear()
        chatList.addAll(messages)
        notifyItemChanged(chatList.size - 1)
    }

    fun resetMessages() {
        chatList.clear()
        notifyDataSetChanged()
    }

    private fun timestampToTime(timestamp: Timestamp): String {
        val date = timestamp.toDate()
        val dateFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
        return dateFormat.format(date)
    }
}