package org.simonolander.lambda.content.exercise

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.data.DialogBuilder
import org.simonolander.lambda.data.Exercise
import org.simonolander.lambda.data.TestCase
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.ui.theme.codeStyle

val kiteExercise = run {
    val name = "Kite"
    val functionName = "ki"

    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that given an input ")
        withStyle(codeStyle) { append("a") }
        append(" produces the identity function ")
        withStyle(codeStyle) { append("λx.x") }
        append(". ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName b") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("λx.x") }
        append(".")
    }

    val testCases = listOf(
        "$functionName a" to "λx.x",
        "$functionName b c" to "c",
        "$functionName d (λx.x) e" to "e",
    ).map(::TestCase)

    val dialog = DialogBuilder()
        .message("The kite, or the ki‑combinator, behaves very similar to the kestrel.")
        .message("When provided two arguments, it always returns the second one.")
        .build()

    Exercise(
        name = name,
        description = description,
        functionName = functionName,
        testCases = testCases,
        dialog = dialog,
    )
}
