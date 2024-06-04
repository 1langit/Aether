package com.pkmaether.aether.ui.chatbot

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pkmaether.aether.data.models.ChatMessage
import com.pkmaether.aether.data.models.Participant
import com.pkmaether.aether.databinding.ItemChatLeftBinding
import com.pkmaether.aether.databinding.ItemChatRightBinding

class ChatAdapter(private val listOfChat: List<ChatMessage>) : RecyclerView.Adapter<ViewHolder>() {

    companion object {
        const val RIGHT_VIEW = 1
        const val LEFT_VIEW = 2
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
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

    override fun getItemCount(): Int = listOfChat.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (listOfChat[position].participant == Participant.USER)
            (holder as RightViewHolder).bind(listOfChat[position])
        else
            (holder as LeftViewHolder).bind(listOfChat[position])
    }

    override fun getItemViewType(position: Int): Int {
        return if (listOfChat[position].participant == Participant.USER) RIGHT_VIEW else LEFT_VIEW
    }

    inner class LeftViewHolder(private val binding: ItemChatLeftBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatMessage) {
            binding.apply {
                chat.also {
                    txtMessage.text = chat.text
//                    txtTime.text = chat.displayTime
                }
            }
        }
    }

    inner class RightViewHolder(private val binding: ItemChatRightBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: ChatMessage) {
            binding.apply {
                chat.also {
                    txtMessage.text = chat.text
//                    txtTime.text = chat.displayTime
                }
            }
        }
    }
}