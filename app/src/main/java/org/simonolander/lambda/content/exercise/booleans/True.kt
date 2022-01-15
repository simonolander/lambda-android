package org.simonolander.lambda.content.exercise.booleans

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.Application
import org.simonolander.lambda.engine.Identifier
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
