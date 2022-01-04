package org.simonolander.lambda.content.exercise

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.data.DialogBuilder
import org.simonolander.lambda.data.Exercise
import org.simonolander.lambda.data.TestCase
import org.simonolander.lambda.engine.*
import org.simonolander.lambda.misc.lambda
import org.simonolander.lambda.ui.theme.codeStyle

val applicatorExercise = run {
    val name = "Apply"
    val functionName = "apply"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes two inputs ")
        withStyle(codeStyle) { append("f") }
        append(" and ")
        withStyle(codeStyle) { append("x") }
        append(", and produces the result of applying ")
        withStyle(codeStyle) { append("f") }
        append(" to ")
        withStyle(codeStyle) { append("x") }
        append(". ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName (λx.x) y") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("y") }
        append(".")
    }

    val testCases = listOf(
        "$functionName id x" to "x",
        "$functionName const x y" to "x",
        "$functionName ki x y" to "y",
        "$functionName f x" to "f x",
    ).map(::TestCase)

    val library = mapOf(
        ID,
        KITE,
        CONST,
    )

    val dialog = DialogBuilder()
        .message("In this exercise, notice that you have a few library functions available to you.")
        .message("You may use them in your function, but this time they wont really be useful to you.")
        .message("I included them to give you more interesting test cases!")
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
