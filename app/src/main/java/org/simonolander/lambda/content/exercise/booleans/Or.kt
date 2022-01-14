package org.simonolander.lambda.content.exercise.booleans

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.FALSE
import org.simonolander.lambda.engine.Identifier
import org.simonolander.lambda.engine.TRUE
import org.simonolander.lambda.engine.parse
import org.simonolander.lambda.ui.theme.codeStyle

val orExercise = run {
    val functionName = "or"
    Exercise(
        name = "Or",
        description = buildAnnotatedString {
            append("Design a function ")
            withStyle(codeStyle) { append(functionName) }
            append(", that takes two boolean inputs, ")
            withStyle(codeStyle) { append("x") }
            append(" and ")
            withStyle(codeStyle) { append("y") }
            append(", and produces the output ")
            withStyle(codeStyle) { append("x ∨ y") }
            append(". ")
            append("\n\n")
            append("It should satisfy the following truth table:\n")
            withStyle(codeStyle) {
                append("$functionName true true   | true")
                append("\n")
                append("$functionName true false  | true")
                append("\n")
                append("$functionName false true  | true")
                append("\n")
                append("$functionName false false | false")
            }
        },
        functionName = functionName,
        testCases = listOf(
            true to true,
            true to false,
            false to true,
            false to false,
        ).map { (a, b) ->
            TestCase(
                input = parse("$functionName $a $b"),
                output = Identifier("${a || b}"),
            )
        },
        library = mapOf(
            TRUE,
            FALSE,
        )
    )
}
