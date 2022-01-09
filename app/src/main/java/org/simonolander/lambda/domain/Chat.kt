package org.simonolander.lambda.domain

data class Chat(
    val messages: List<ChatMessage>,
    val typing: Boolean,
)
