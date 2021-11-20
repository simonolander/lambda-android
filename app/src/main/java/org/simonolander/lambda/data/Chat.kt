package org.simonolander.lambda.data

data class Chat(
    val messages: List<ChatMessage>,
    val typing: Boolean,
)
