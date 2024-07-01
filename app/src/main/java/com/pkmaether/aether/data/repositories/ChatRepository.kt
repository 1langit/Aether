package com.pkmaether.aether.data.repositories

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.pkmaether.aether.data.PrefManager
import com.pkmaether.aether.data.firebase.FirebaseAuthClient
import com.pkmaether.aether.data.firebase.FirestoreClient
import com.pkmaether.aether.data.models.ChatMessage
import kotlinx.coroutines.tasks.await

class ChatRepository {

    private val firestoreInstance: FirebaseFirestore
        get() = FirestoreClient.firestoreInstance

    val messageSubcollection = firestoreInstance.collection("chats")
        .document(FirebaseAuthClient.authInstance.uid!!)
        .collection("messages")

    fun pushChats(userChat: ChatMessage, modelChat: ChatMessage) {
        userChat.id = messageSubcollection.document().id
        modelChat.id = messageSubcollection.document().id
        val userChatRef = messageSubcollection.document(userChat.id)
        val modelChatRef = messageSubcollection.document(modelChat.id)

        val batch = firestoreInstance.batch()
        batch.set(userChatRef, userChat)
        batch.set(modelChatRef, modelChat)

        batch.commit()
    }

    suspend fun getChatHistory(): List<ChatMessage> {
        return try {
            val querySnapshot = messageSubcollection.get().await()
            val chatMessages = mutableListOf<ChatMessage>()
            for (document in querySnapshot.documents) {
                document.toObject(ChatMessage::class.java)?.let { chatMessages.add(it) }
            }
            chatMessages
        } catch (e: Exception) {
            emptyList()
        }
    }
}