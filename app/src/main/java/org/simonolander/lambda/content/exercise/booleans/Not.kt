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

val notExercise = run {
    val functionName = "not"
    Exercise(
        name = "Not",
        description = buildAnnotatedString {
            append("Design a function ")
            withStyle(codeStyle) { append(functionName) }
            append(", that takes a boolean input, ")
            withStyle(codeStyle) { append("x") }
            append(", and produces the output ")
            withStyle(codeStyle) { append("¬x") }
            append(". ")
            append("\n\n")
            append("It should satisfy the following truth table:\n")
            withStyle(codeStyle) {
                append("$functionName true  | false")
                append("\n")
                append("$functionName false | true")
            }
        },
        functionName = functionName,
        testCases = listOf(true, false).map { a ->
            TestCase(
                input = parse("$functionName $a"),
                output = Identifier("${!a}"),
            )
        },
        library = mapOf(
            TRUE,
            FALSE,
        )
    )
}
