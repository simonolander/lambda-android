package org.simonolander.lambda.content.exercise.basic

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.ui.theme.codeStyle

val kestrelExercise = run {
    val name = "Const (or kestrel)"
    val functionName = "const"

    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that given an input ")
        withStyle(codeStyle) { append("x") }
        append(" produces the constant function ")
        withStyle(codeStyle) { append("λa.x") }
        append(". ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName b") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("λa.b") }
        append(".")
    }

    val testCases = listOf(
        "$functionName a" to "λx.a",
        "$functionName b c" to "b",
        "$functionName (λx.x) d e" to "e",
    ).map(::TestCase)

    val dialog = DialogBuilder()
        .message("The $lambdaCalculus community has a thing where they name some common functions after birds.")
        .message("The kestrel, also called const or the k‑combinator, is one of these functions.")
        .message("When provided two arguments, it always returns the first one.")
        .build()

    Exercise(
        name = name,
        description = description,
        functionName = functionName,
        testCases = testCases,
        dialog = dialog,
    )
}
