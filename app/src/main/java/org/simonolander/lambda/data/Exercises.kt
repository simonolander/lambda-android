package org.simonolander.lambda.data

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.engine.Application
import org.simonolander.lambda.engine.Identifier
import org.simonolander.lambda.ui.theme.codeStyle

val identity = Exercise(
    name = "Identity",
    description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append("id") }
        append(", that given any single input ")
        withStyle(codeStyle) { append("x") }
        append(" produces the same output ")
        withStyle(codeStyle) { append("x") }
        append(". ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("id a") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("a") }
        append(".")
    },
    testCases = listOf(
        Identifier("a"),
        Identifier("value"),
        Identifier("⛄️"),
    ).map { arg ->
        TestCase(
            input = Application(
                function = Identifier("id"),
                argument = arg,
            ),
            output = arg,
        )
    }
)
