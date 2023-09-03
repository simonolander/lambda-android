package org.simonolander.lambda.content.exercise.numbers

import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.TestCase
import org.simonolander.lambda.engine.CONST
import org.simonolander.lambda.engine.FALSE
import org.simonolander.lambda.engine.ID
import org.simonolander.lambda.engine.IF
import org.simonolander.lambda.engine.TRUE
import org.simonolander.lambda.engine.ZERO
import org.simonolander.lambda.engine.churchNumerals
import org.simonolander.lambda.ui.theme.codeStyle
import kotlin.math.max

val predecessorExercise = run {
    val name = "Predecessor"
    val functionName = "pred"
    val description = buildAnnotatedString {
        append("Design a function ")
        withStyle(codeStyle) { append(functionName) }
        append(", that takes a number ")
        withStyle(codeStyle) { append("n") }
        append(", and produces ")
        withStyle(codeStyle) { append("n - 1") }
        append(" if the number is greater than zero, and ")
        withStyle(codeStyle) { append("0") }
        append(" otherwise. ")
        append("\n\n")
        append("For example, ")
        withStyle(codeStyle) { append("$functionName 3") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("2") }
        append(", and ")
        withStyle(codeStyle) { append("$functionName 0") }
        append(" should reduce to ")
        withStyle(codeStyle) { append("0") }
        append(".")
    }

    val testCases = listOf(
        3,
        2,
        1,
        0,
    ).map { n ->
        TestCase("$functionName $n", max(n - 1, 0))
    }

    val library = mapOf(
        TRUE,
        FALSE,
        IF,
        CONST,
        ZERO,
        ID,
        *churchNumerals(3),
    )

    val dialog = DialogBuilder()
        .message("The predecessor function, pred, is in a way the opposite of succ.")
        .message("It produces the number that is one smaller than the input, except for zero which returns zero.")
        .message("This lesson is difficult, and there are many correct answers. Good luck!")
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
