package org.simonolander.lambda.domain

import androidx.compose.runtime.Composable

sealed interface Dialog

data class Message(
    val text: String,
    val view: (@Composable () -> Unit)? = null,
    val next: Dialog? = null,
) : Dialog

data class Question(
    val text: String,
    val responses: List<Response>,
) : Dialog

typealias Response = Pair<String, Dialog?>

class DialogBuilder {
    fun message(text: String, view: @Composable (() -> Unit)? = null): DialogNonNullBuilder {
        return DialogNonNullBuilder(Message(text, view))
    }
}

class DialogNonNullBuilder(initialMessage: Message) {

    private var lastMessage = initialMessage
    private val messages = mutableListOf<Message>()

    fun message(text: String, view: @Composable (() -> Unit)? = null): DialogNonNullBuilder {
        messages += lastMessage
        lastMessage = Message(text, view)
        return this
    }

    fun question(text: String, vararg responses: Response): Dialog {
        return build(Question(text, responses.toList()))
    }

    fun build(): Dialog {
        return messages.foldRight(lastMessage) { message, acc ->
            Message(
                text = message.text,
                view = message.view,
                next = acc,
            )
        }
    }

    fun build(tail: Dialog): Dialog {
        val lastMessage = Message(
            text = lastMessage.text,
            view = lastMessage.view,
            next = tail,
        )
        return messages.foldRight(lastMessage) { message, acc ->
            Message(
                text = message.text,
                view = message.view,
                next = acc,
            )
        }
    }
}
