package org.simonolander.lambda.data

sealed interface Dialog

data class Message(
    val text: String,
    val next: Dialog = DialogEnd
): Dialog {
    fun withNext(next: Dialog): Message {
        return Message(
            text = this.text,
            next = next
        )
    }
}

data class Question(
    val text: String,
    val responses: List<Response>
): Dialog

typealias Response = Pair<String, Dialog>

object DialogEnd: Dialog

class DialogBuilder {
    private val messages = mutableListOf<Message>()
    private var question: Question? = null

    fun message(text: String): DialogBuilder {
        messages += Message(text)
        return this
    }

    fun choice(text: String, vararg responses: Pair<String, Dialog>): Dialog {
        question = Question(text, responses.toList())
        return build()
    }

    fun build(): Dialog {
        return messages.foldRight(question ?: DialogEnd) { message, dialog ->
            message.withNext(dialog)
        }
    }
}

fun buildDialog(builderAction: DialogBuilder.() -> Unit): Dialog {
    return DialogBuilder().apply(builderAction).build()
}
