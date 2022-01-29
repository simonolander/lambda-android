package org.simonolander.lambda.content.exercise.numbers

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.churchNumeral
import org.simonolander.lambda.ui.theme.codeStyle

val successorExercise = run {
    val name = "Successor"
    val functionName = "succ"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes a number ")
        withStyle(codeStyle) { append("n") }
        append(", and produces ")
        withStyle(codeStyle) { append("n + 1") }
        append(". ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName 3") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("4") }
        append(".")
    }

    val testCases = listOf(
        "$functionName 0" to "1",
        "$functionName 1" to "2",
        "$functionName 2" to "3",
        "$functionName 5" to "6",
    ).map(::TestCase)

    val library = mapOf(
        churchNumeral(0),
        churchNumeral(1),
        churchNumeral(2),
        churchNumeral(3),
        churchNumeral(4),
        churchNumeral(5),
        churchNumeral(6),
    )

    val dialog = DialogBuilder()
        .message("The successor function is a staple in functional programming!")
        .message("It returns the number that's one bigger than the input.")
        .build()

    Exercise(
        name = name,
        description = description,
        functionName = functionName,
        testCases = testCases,
        library = library,
        dialog = dialog,
    )
}
