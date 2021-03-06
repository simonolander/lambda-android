package org.simonolander.lambda.domain

import androidx.compose.ui.text.AnnotatedString

data class ChatMessage(
    val text: AnnotatedString,
    val sent: Boolean = false,
) {
    constructor(
        text: String,
        sent: Boolean = false,
    ) : this(AnnotatedString(text), sent)
}
