package com.pkmaether.aether.data.repositories

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.google.firebase.Timestamp
import com.pkmaether.aether.data.database.ChatDatabaseHelper
import com.pkmaether.aether.data.models.ChatMessage
import com.pkmaether.aether.data.models.Participant

class ChatLocalRepository(context: Context) {

    private val dbHelper = ChatDatabaseHelper(context)

    fun insertMessage(message: ChatMessage): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(ChatDatabaseHelper.COLUMN_ID, message.id)
            put(ChatDatabaseHelper.COLUMN_PARTICIPANT, message.participant.name)
            put(ChatDatabaseHelper.COLUMN_TEXT, message.text)
            put(ChatDatabaseHelper.COLUMN_TIMESTAMP, message.timestamp.seconds)
        }
        val cursor = db.query(
            ChatDatabaseHelper.TABLE_NAME,
            arrayOf("id"),
            "id = ?",
            arrayOf(message.id),
            null,
            null,
            null
        )
        if (cursor.count == 0) {
            db.insertOrThrow(ChatDatabaseHelper.TABLE_NAME, null, values)
            cursor.close()
            return true
        }
        cursor.close()
        return false
    }

    fun getChatHistory(): List<ChatMessage> {
        val db = dbHelper.readableDatabase
        val projection = arrayOf(
            ChatDatabaseHelper.COLUMN_ID,
            ChatDatabaseHelper.COLUMN_PARTICIPANT,
            ChatDatabaseHelper.COLUMN_TEXT,
            ChatDatabaseHelper.COLUMN_TIMESTAMP
        )
        val cursor: Cursor = db.query(
            ChatDatabaseHelper.TABLE_NAME,
            projection,
            null,
            null,
            null,
            null,
            "${ChatDatabaseHelper.COLUMN_TIMESTAMP} ASC"
        )

        val messages = mutableListOf<ChatMessage>()
        with(cursor) {
            while (moveToNext()) {
                val id = getString(getColumnIndexOrThrow(ChatDatabaseHelper.COLUMN_ID))
                val participant = getString(getColumnIndexOrThrow(ChatDatabaseHelper.COLUMN_PARTICIPANT))
                val text = getString(getColumnIndexOrThrow(ChatDatabaseHelper.COLUMN_TEXT))
                val timestampSeconds = getLong(getColumnIndexOrThrow(ChatDatabaseHelper.COLUMN_TIMESTAMP))
                val timestamp = Timestamp(timestampSeconds, 0)
                messages.add(ChatMessage(id, text, Participant.valueOf(participant), timestamp))
            }
        }
        cursor.close()
        return messages
    }

    fun clearChatHistory() {
        val db = dbHelper.writableDatabase
        db.delete(ChatDatabaseHelper.TABLE_NAME, null, null)
    }
}