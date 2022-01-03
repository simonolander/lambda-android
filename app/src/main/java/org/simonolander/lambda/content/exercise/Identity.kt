package org.simonolander.lambda.content.exercise

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.content.dialog.applicationSyntaxDialog
import org.simonolander.lambda.data.DialogBuilder
import org.simonolander.lambda.data.Exercise
import org.simonolander.lambda.data.TestCase
import org.simonolander.lambda.engine.Application
import org.simonolander.lambda.engine.Identifier
import org.simonolander.lambda.misc.lambda
import org.simonolander.lambda.ui.theme.codeStyle

val identityExercise = run {
    val functionName = "id"

    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that given any single input ")
        withStyle(codeStyle) { append("x") }
        append(" produces the same output ")
        withStyle(codeStyle) { append("x") }
        append(". ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("id a") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("a") }
        append(".")
    }

    val testCases = listOf(
        "a",
        "value",
        "⛄️",
    ).map { arg ->
        TestCase(
            input = Application(
                function = Identifier(functionName),
                argument = Identifier(arg),
            ),
            output = Identifier(arg),
        )
    }

    val dialog = DialogBuilder()
        .message("Hello!")
        .message("Enough talking, it's time for some action!")
        .message("Here, you need to enter an the expression that satisfies the given exercise.")
        .message("In this instance, I'm asking you to design the identity function.")
        .message("Write it in the text field called id.")
        .message("If you don't have a $lambda on your keyboard, you can simply click the icon in the text field.")
        .message("Once you're happy with your function, click run and we'll see if it works!")
        .build()

    Exercise(
        name = "Identity",
        description = description,
        functionName = functionName,
        testCases = testCases,
        dialog = dialog,
    )
}
