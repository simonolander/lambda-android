package org.simonolander.lambda.content.exercise.numbers

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.CONST
import org.simonolander.lambda.engine.FALSE
import org.simonolander.lambda.engine.IF
import org.simonolander.lambda.engine.NOT
import org.simonolander.lambda.engine.TRUE
import org.simonolander.lambda.engine.churchNumerals
import org.simonolander.lambda.ui.theme.codeStyle

val evenExercise = run {
    val name = "Even numbers"
    val functionName = "even"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes a number ")
        withStyle(codeStyle) { append("n") }
        append(", and produces ")
        withStyle(codeStyle) { append("true") }
        append(" if the number is even, and ")
        withStyle(codeStyle) { append("false") }
        append(" if the number is odd. ")
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
        1,
        4,
        0,
        3,
        5,
    ).map { n ->
        TestCase("$functionName $n", n % 2 == 0)
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
