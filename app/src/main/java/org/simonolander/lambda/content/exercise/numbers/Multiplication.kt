package org.simonolander.lambda.content.exercise.numbers

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.*
import org.simonolander.lambda.ui.theme.codeStyle

val multiplicationExercise = run {
    val name = "Multiplication"
    val functionName = "mult"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes two numbers ")
        withStyle(codeStyle) { append("a") }
        append(" and ")
        withStyle(codeStyle) { append("b") }
        append(", and produces ")
        withStyle(codeStyle) { append("a · b") }
        append(". ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName 3 4") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("12") }
        append(".")
    }

    val testCases = listOf(
        0 to 0,
        0 to 2,
        1 to 0,
        1 to 1,
        5 to 1,
        3 to 2,
        4 to 3,
    ).map { (a, b) ->
        TestCase("$functionName $a $b", a * b)
    }

    val library = mapOf(
        SUCC,
        ADD,
        churchNumeral(0),
        churchNumeral(1),
        churchNumeral(2),
        churchNumeral(3),
        churchNumeral(4),
        churchNumeral(5),
        churchNumeral(6),
        churchNumeral(12),
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
