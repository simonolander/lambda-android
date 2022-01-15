package org.simonolander.lambda.content.exercise.numbers

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.*
import org.simonolander.lambda.ui.theme.codeStyle

val subtractionExercise = run {
    val name = "Subtraction"
    val functionName = "sub"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes two numbers ")
        withStyle(codeStyle) { append("a") }
        append(" and ")
        withStyle(codeStyle) { append("b") }
        append(", and produces ")
        withStyle(codeStyle) { append("a - b") }
        append(". If ")
        withStyle(codeStyle) { append("b") }
        append(" is greater than ")
        withStyle(codeStyle) { append("a") }
        append(", return ")
        withStyle(codeStyle) { append("0") }
        append(".")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName 5 2") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("3") }
        append(", and ")
        withStyle(codeStyle) { append("$functionName 2 5") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("0") }
        append(".")
    }

    val testCases = listOf(
        5 to 2,
        2 to 5,
        3 to 3,
        0 to 4,
        1 to 0,
    ).map { (a, b) ->
        TestCase("$functionName a b", maxOf(a - b, 0))
    }

    val library = mapOf(
        PRED,
        *churchNumerals(5),
    )

    val dialog = DialogBuilder()
        .message("Now that we have our predecessor function, we can finally do minus!")
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
