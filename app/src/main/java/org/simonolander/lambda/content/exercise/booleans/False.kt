package org.simonolander.lambda.content.exercise.booleans

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.Application
import org.simonolander.lambda.engine.Identifier
import org.simonolander.lambda.ui.theme.codeStyle

val falseExercise = run {
    val functionName = "false"
    Exercise(
        name = "False",
        description = buildAnnotatedString {
            append("Design a function ")
            withStyle(codeStyle) { append(functionName) }
            append(", that takes two inputs, ")
            withStyle(codeStyle) { append("x") }
            append(" and ")
            withStyle(codeStyle) { append("y") }
            append(", and produces the output ")
            withStyle(codeStyle) { append("y") }
            append(". ")
            append("\n\n")
            append("For example, ")
            withStyle(codeStyle) { append("$functionName a b") }
            append(" should reduce to ")
            withStyle(codeStyle) { append("b") }
            append(".")
        },
        functionName = functionName,
        testCases = listOf(
            Identifier("a") to Identifier("b"),
            Identifier("x") to Identifier("y"),
            Identifier("c") to Identifier("c"),
        ).map { (a, b) ->
            TestCase(
                input = Application(
                    function = Application(
                        Identifier(functionName),
                        a,
                    ),
                    argument = b,
                ),
                output = b,
            )
        }
    )
}
