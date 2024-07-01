package com.pkmaether.aether.data.models

import com.google.firebase.Timestamp
import java.util.UUID

data class ChatMessage(
    var id: String = UUID.randomUUID().toString(),
    var text: String = "",
    val participant: Participant = Participant.USER,
    val timestamp: Timestamp = Timestamp.now()
)