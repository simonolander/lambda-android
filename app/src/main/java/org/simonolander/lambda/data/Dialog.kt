package org.simonolander.lambda.data

sealed interface Dialog

data class Message(
    val text: String = "< To be continued >",
    val next: Dialog? = null,
) : Dialog

data class Question(
    val text: String,
    val responses: List<Response>,
) : Dialog

typealias Response = Pair<String, Dialog?>

class DialogBuilder {
    fun message(text: String): DialogNonNullBuilder {
        return DialogNonNullBuilder(text)
    }

    fun question(text: String, vararg responses: Response): Dialog {
        return Question(text, responses.toList())
    }
}

class DialogNonNullBuilder(initialMessage: String) {

    private val messages = mutableListOf(initialMessage)

    fun message(text: String): DialogNonNullBuilder {
        messages += text
        return this
    }

    fun question(text: String, vararg responses: Response): Dialog {
        return build(Question(text, responses.toList()))
    }

    fun build(): Dialog {
        require(messages.isNotEmpty())
        return messages.foldRight<String, Message?>(null) { text, acc ->
            Message(text, acc)
        }!!
    }

    fun build(tail: Dialog): Dialog {
        return messages.foldRight(tail) { text, acc ->
            Message(text, acc)
        }
    }
}
