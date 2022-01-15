package org.simonolander.lambda.content.exercise.numbers

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.*
import org.simonolander.lambda.ui.theme.codeStyle

val oddExercise = run {
    val name = "Odd numbers"
    val functionName = "odd"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes a number ")
        withStyle(codeStyle) { append("n") }
        append(", and produces ")
        withStyle(codeStyle) { append("true") }
        append(" if the number is odd, and ")
        withStyle(codeStyle) { append("false") }
        append(" if the number is even. ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName 0") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("false") }
        append(", and ")
        withStyle(codeStyle) { append("$functionName 5") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("true") }
        append(".")
    }

    val testCases = listOf(
        1,
        4,
        0,
        3,
        5,
    ).map { n ->
        TestCase("$functionName $n", n % 2 == 1)
    }

    val library = mapOf(
        TRUE,
        FALSE,
        IF,
        CONST,
        NOT,
        *churchNumerals(5),
    )

    val dialog = null

    Exercise(
        name = name,
        description = description,
        functionName = functionName,
        testCases = testCases,
        library = library,
        dialog = dialog,
    )
}
