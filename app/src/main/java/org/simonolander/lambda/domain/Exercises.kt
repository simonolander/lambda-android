package org.simonolander.lambda.domain

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.engine.*
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.ui.theme.codeStyle

val trueExercise = run {
    val functionName = "true"

    val dialog = run {
        DialogBuilder()
            .message("Hello!")
            .message("In this lesson we're going to be exploring booleans in $lambdaCalculus.")
            .message("A boolean is a type of data that can be one of two values: true or false.")
            .message("Booleans are used primarily for deciding between two outcomes.")
            .message("Like everything else in $lambdaCalculus, we represent booleans using functions.")
            .message("A boolean in $lambdaCalculus is simply a function, that when presented with two arguments, returns one of them.")
            .message("The boolean true chooses the first argument. False chooses the second.")
            .message("Please go ahead and write the true function for me.")
            .message("I'll give you a hint, you've already seen this function in a previous lesson.")
            .build()
    }

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
        },
        dialog = dialog,
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
            FALSE,
        )
    )
}

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

val ifExercise = run {
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

val xorExercise = run {
    val functionName = "xor"
    Exercise(
        name = "Exclusive Or",
        description = buildAnnotatedString {
            append("Design a function ")
            withStyle(codeStyle) { append(functionName) }
            append(", that takes two boolean inputs, ")
            withStyle(codeStyle) { append("x") }
            append(" and ")
            withStyle(codeStyle) { append("y") }
            append(", and produces the output ")
            withStyle(codeStyle) { append("x ⊕ y") }
            append(". ")
            append("\n\n")
            append("It should satisfy the following truth table:\n")
            withStyle(codeStyle) {
                append("$functionName true true   | false")
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
                output = Identifier("${a xor b}"),
            )
        },
        library = mapOf(
            TRUE,
            FALSE,
            NOT,
            AND,
            OR,
            IF,
        )
    )
}
