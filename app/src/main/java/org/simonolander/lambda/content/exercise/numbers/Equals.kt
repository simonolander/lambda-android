package org.simonolander.lambda.content.exercise.numbers

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.*
import org.simonolander.lambda.ui.theme.codeStyle

val equalExercise = run {
    val name = "Equals"
    val functionName = "eq"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes two numbers ")
        withStyle(codeStyle) { append("a") }
        append(" and ")
        withStyle(codeStyle) { append("b") }
        append(", and produces ")
        withStyle(codeStyle) { append("a = b") }
        append(".")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName 1 2") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("false") }
        append(", and ")
        withStyle(codeStyle) { append("$functionName 2 2") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("true") }
        append(".")
    }

    val testCases = listOf(
        5 to 2,
        3 to 3,
        0 to 1,
    ).map { (a, b) ->
        TestCase("$functionName $a $b", a == b)
    }

    val library = mapOf(
        PRED,
        SUB,
        AND,
        LEQ,
        OR,
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
