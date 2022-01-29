package org.simonolander.lambda.content.exercise.numbers

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.SUCC
import org.simonolander.lambda.engine.churchNumeral
import org.simonolander.lambda.ui.theme.codeStyle

val additionExercise = run {
    val name = "Addition"
    val functionName = "add"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes two numbers ")
        withStyle(codeStyle) { append("a") }
        append(" and ")
        withStyle(codeStyle) { append("b") }
        append(", and produces ")
        withStyle(codeStyle) { append("a + b") }
        append(". ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName 3 4") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("7") }
        append(".")
    }

    val testCases = listOf(
        "$functionName 1 1" to "2",
        "$functionName 3 2" to "5",
        "$functionName 2 0" to "2",
        "$functionName 0 0" to "0",
        "$functionName 0 4" to "4",
        "$functionName 3 3" to "6",
    ).map(::TestCase)

    val library = mapOf(
        SUCC,
        churchNumeral(0),
        churchNumeral(1),
        churchNumeral(2),
        churchNumeral(3),
        churchNumeral(4),
        churchNumeral(5),
        churchNumeral(6),
    )

    val dialog = DialogBuilder()
        .message("Lets see if you can use the successor function to create a function that adds two numbers!")
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
