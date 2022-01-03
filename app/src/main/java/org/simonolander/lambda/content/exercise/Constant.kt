package org.simonolander.lambda.content.exercise

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.data.DialogBuilder
import org.simonolander.lambda.data.Exercise
import org.simonolander.lambda.data.TestCase
import org.simonolander.lambda.engine.Application
import org.simonolander.lambda.engine.Function
import org.simonolander.lambda.engine.Identifier
import org.simonolander.lambda.ui.theme.codeStyle

val constantExercise = run {
    val name = "A constant function"
    val functionName = "const_a"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that for every input ")
        withStyle(codeStyle) { append("x") }
        append(" produces the output ")
        withStyle(codeStyle) { append("a") }
        append(". ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName x") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("a") }
        append(".")
    }
    val testCases = listOf(
        Identifier("x"),
        Identifier("a"),
        Function("x", Identifier("x")),
    ).map { arg ->
        TestCase(
            input = Application(
                function = Identifier(functionName),
                argument = arg,
            ),
            output = Identifier("a"),
        )
    }

    val dialog = DialogBuilder()
        .message("Constant functions always return the same thing, regardless of their inputs.")
        .message("They can be very useful!")
        .build()

    Exercise(
        name = name,
        description = description,
        functionName = functionName,
        testCases = testCases,
        dialog = dialog,
    )
}
