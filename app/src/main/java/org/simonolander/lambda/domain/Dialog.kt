package org.simonolander.lambda.domain

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable

sealed interface Dialog

data class Message(
    val text: String,
    val view: (@Composable (BoxScope.() -> Unit))? = null,
    val next: Dialog? = null,
    val speaker: Character = Character.Lambert,
) : Dialog {
    fun withNext(next: Dialog?): Message {
        return Message(text, view, next, speaker)
    }
}

data class Question(
    val text: String,
    val responses: List<Response>,
) : Dialog

typealias Response = Pair<String, Dialog?>

class DialogBuilder {
    fun message(
        text: String,
        speaker: Character = Character.Lambert,
        view: @Composable (BoxScope.() -> Unit)? = null,
    ): DialogNonNullBuilder {
        return DialogNonNullBuilder(Message(
            text = text,
            speaker = speaker,
            view = view,
        ))
    }
}

class DialogNonNullBuilder(initialMessage: Message) {

    private var lastMessage = initialMessage
    private val messages = mutableListOf<Message>()

    fun message(
        text: String,
        speaker: Character = Character.Lambert,
        view: @Composable (BoxScope.() -> Unit)? = null,
    ): DialogNonNullBuilder {
        messages += lastMessage
        lastMessage = Message(text, view, speaker = speaker)
        return this
    }

    fun question(text: String, vararg responses: Response): Dialog {
        return build(Question(text, responses.toList()))
    }

    fun build(tail: Dialog? = null): Dialog {
        return messages.foldRight(lastMessage.withNext(tail)) { message, acc ->
            message.withNext(acc)
        }
    }
}
