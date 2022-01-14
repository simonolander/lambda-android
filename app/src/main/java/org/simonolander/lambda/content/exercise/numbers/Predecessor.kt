package org.simonolander.lambda.content.exercise.numbers

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.*
import org.simonolander.lambda.ui.theme.codeStyle

val predecessorExercise = run {
    val name = "Predecessor"
    val functionName = "pred"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes a number ")
        withStyle(codeStyle) { append("n") }
        append(", and produces ")
        withStyle(codeStyle) { append("n - 1") }
        append(" if the number is greater than zero, and ")
        withStyle(codeStyle) { append("0") }
        append(" otherwise. ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName 3") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("2") }
        append(", and")
        withStyle(codeStyle) { append("$functionName 0") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("0") }
        append(".")
    }

    val testCases = listOf(
        "$functionName 3" to "2",
        "$functionName 2" to "1",
        "$functionName 1" to "0",
        "$functionName 0" to "0",
    ).map(::TestCase)

    val library = mapOf(
        TRUE,
        FALSE,
        IF,
        CONST,
        IS_ZERO,
        *churchNumerals(3),
    )

    val dialog = DialogBuilder()
        .message("The predecessor function, pred, is in a way the opposite of succ.")
        .message("It produces the number that is one smaller than the input, except for zero which returns zero.")
        .message("This lesson is difficult, and there are many correct answers. Good luck!")
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
