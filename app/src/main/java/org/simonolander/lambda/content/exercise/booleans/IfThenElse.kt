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

val ifThenElseExercise = run {
    val functionName = "if"
    Exercise(
        name = "If-then-else",
        description = buildAnnotatedString {
            append("Design a function ")
            withStyle(codeStyle) { append(functionName) }
            append(", that takes one boolean input, ")
            withStyle(codeStyle) { append("p") }
            append(" and two arbitrary inputs ")
            withStyle(codeStyle) { append("x") }
            append(" and ")
            withStyle(codeStyle) { append("y") }
            append(". If ")
            withStyle(codeStyle) { append("p") }
            append(" is true, then it should return ")
            withStyle(codeStyle) { append("x") }
            append(" otherwise ")
            withStyle(codeStyle) { append("y") }
            append(".")
        },
        functionName = functionName,
        testCases = listOf(
            Triple(true, "a", "b"),
            Triple(false, "a", "b"),
        ).map { (a, b, c) ->
            TestCase(
                input = parse("$functionName $a $b $c"),
                output = Identifier(if (a) b else c),
            )
        },
        library = mapOf(
            TRUE,
            FALSE,
        )
    )
}
