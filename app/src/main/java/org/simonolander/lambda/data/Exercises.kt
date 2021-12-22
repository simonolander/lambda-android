package org.simonolander.lambda.data

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.engine.*
import org.simonolander.lambda.engine.Function
import org.simonolander.lambda.ui.theme.codeStyle

val identity = run {
    val functionName = "id"
    Exercise(
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
        functionName = functionName,
        testCases = listOf(
            Identifier("a"),
            Identifier("value"),
            Identifier("⛄️"),
        ).map { arg ->
            TestCase(
                input = Application(
                    function = Identifier(functionName),
                    argument = arg,
                ),
                output = arg,
            )
        }
    )
}

val constantFunctionExercise = run {
    val functionName = "always_a"
    Exercise(
        name = "Constant Function",
        description = buildAnnotatedString {
            append("Design a function ")
            withStyle(codeStyle) { append("always_a") }
            append(", that for every input ")
            withStyle(codeStyle) { append("x") }
            append(" produces the output ")
            withStyle(codeStyle) { append("a") }
            append(". ")
            append("\n\n")
            append("For example, ")
            withStyle(codeStyle) { append("always_a x") }
            append(" should reduce to ")
            withStyle(codeStyle) { append("a") }
            append(".")
        },
        functionName = functionName,
        testCases = listOf(
            Identifier("x"),
            Identifier("a"),
            Function("x", Identifier("x")),
        ).map { arg ->
            TestCase(
                input = Application(
                    function = Identifier(functionName),
                    argument = arg,
                ),
                output = Identifier("a"),
            )
        }
    )
}

val trueExercise = run {
    val functionName = "true"
    Exercise(
        name = "True",
        description = buildAnnotatedString {
            append("Design a function ")
            withStyle(codeStyle) { append(functionName) }
            append(", that takes two inputs, ")
            withStyle(codeStyle) { append("x") }
            append(" and ")
            withStyle(codeStyle) { append("y") }
            append(", and produces the output ")
            withStyle(codeStyle) { append("x") }
            append(". ")
            append("\n\n")
            append("For example, ")
            withStyle(codeStyle) { append("$functionName a b") }
            append(" should reduce to ")
            withStyle(codeStyle) { append("a") }
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
                output = a,
            )
        }
    )
}

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

val andExercise = run {
    val functionName = "and"
    Exercise(
        name = "And",
        description = buildAnnotatedString {
            append("Design a function ")
            withStyle(codeStyle) { append(functionName) }
            append(", that takes two boolean inputs, ")
            withStyle(codeStyle) { append("x") }
            append(" and ")
            withStyle(codeStyle) { append("y") }
            append(", and produces the output ")
            withStyle(codeStyle) { append("x ∧ y") }
            append(". ")
            append("\n\n")
            append("It should satisfy the following truth table:\n")
            withStyle(codeStyle) {
                append("$functionName true true   | true")
                append("\n")
                append("$functionName true false  | false")
                append("\n")
                append("$functionName false true  | false")
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
                output = Identifier("${a && b}"),
            )
        },
        library = mapOf(
            TRUE,
            FALSE
        )
    )
}
