package org.simonolander.lambda.content.exercise.numbers

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.*
import org.simonolander.lambda.ui.theme.codeStyle

val isZeroExercise = run {
    val name = "Is zero"
    val functionName = "isZero"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes a number ")
        withStyle(codeStyle) { append("n") }
        append(", and produces ")
        withStyle(codeStyle) { append("true") }
        append(" if the number is equal to ")
        withStyle(codeStyle) { append("0") }
        append(", and ")
        withStyle(codeStyle) { append("false") }
        append(" otherwise. ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName 0") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("true") }
        append(", and ")
        withStyle(codeStyle) { append("$functionName 5") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("false") }
        append(".")
    }

    val testCases = listOf(
        0,
        1,
        2,
        3,
    ).map { n ->
        TestCase("$functionName $n", n == 0)
    }

    val library = mapOf(
        TRUE,
        FALSE,
        IF,
        CONST,
        *churchNumerals(3),
    )

    val dialog = DialogBuilder()
        .message("Zero is kind of special. It would be nice to have a function that recognizes it.")
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
