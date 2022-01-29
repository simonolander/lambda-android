package org.simonolander.lambda.content.exercise.pairs

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.*
import org.simonolander.lambda.ui.theme.codeStyle

val secondExercise = run {
    val name = "Second"
    val functionName = "snd"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes a pair ")
        withStyle(codeStyle) { append("p") }
        append(", and returns the second value in the pair.")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName (pair a b)") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("b") }
        append(".")
    }

    val testCases = listOf(
        "a" to "b",
        "b" to "c",
    ).map { (a, b) ->
        TestCase("$functionName (pair $a $b)", b)
    }

    val library = mapOf(
        PAIR,
        TRUE,
        FALSE,
        APPLY,
        FLIP,
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
