package org.simonolander.lambda.content.exercise.basic

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.APPLY
import org.simonolander.lambda.engine.CONST
import org.simonolander.lambda.engine.ID
import org.simonolander.lambda.engine.KITE
import org.simonolander.lambda.ui.theme.codeStyle

val cardinalExercise = run {
    val name = "Flip"
    val functionName = "flip"

    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", so that ")
        withStyle(codeStyle) { append("$functionName f x y") }
        append(" equals ")
        withStyle(codeStyle) { append("f y x") }
        append(".")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName const x y") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("y") }
        append(".")
    }

    val testCases = listOf(
        "$functionName const x y" to "y",
        "$functionName const" to "ki",
        "$functionName ki" to "const",
        "$functionName apply x id" to "x",
        "$functionName f x y" to "f y x",
    ).map(::TestCase)

    val library = mapOf(
        ID,
        KITE,
        CONST,
        APPLY,
    )

    val dialog = DialogBuilder()
        .message("The flip function, or the cardinal as it's sometimes called, is quite handy!")
        .message("It swaps the first two arguments of a function.")
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
