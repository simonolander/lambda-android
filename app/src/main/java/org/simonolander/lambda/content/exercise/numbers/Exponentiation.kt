package org.simonolander.lambda.content.exercise.numbers

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.*
import org.simonolander.lambda.ui.theme.codeStyle

val exponentiationExercise = run {
    val name = "Exponentiation"
    val functionName = "pow"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes two numbers ")
        withStyle(codeStyle) { append("a") }
        append(" and ")
        withStyle(codeStyle) { append("b") }
        append(", and produces ")
        withStyle(codeStyle) { append("a ^ b") }
        append(". ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName 3 2") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("9") }
        append(".")
    }

    val testCases = listOf(
        "$functionName 3 2" to "9",
        "$functionName 5 1" to "5",
        "$functionName 6 0" to "1",
        "$functionName 0 0" to "1",
        "$functionName 0 9" to "0",
        "$functionName 2 4" to "16",
    ).map(::TestCase)

    val library = mapOf(
        SUCC,
        ADD,
        MULT,
        *churchNumerals(16)
    )

    val dialog = DialogBuilder()
        .message("Exponentiation, or raising a number to a power, is repeated multiplication.")
        .message("For example, 3^4 is the same as 3·3·3·3, which is 81.")
        .message("For any number n, including n=0, n^0 is 1.")
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
