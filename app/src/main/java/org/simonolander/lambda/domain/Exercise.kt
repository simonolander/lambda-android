package org.simonolander.lambda.domain

import androidx.compose.ui.text.AnnotatedString
import org.simonolander.lambda.engine.Expression

/**
 * An exercise represents a problem to which the user must supply a program
 * that given the parameters of the exercise produces the correct output.
 */
data class Exercise(
    val name: String,
    val description: AnnotatedString,
    val functionName: String,
    val testCases: List<TestCase>,
    val library: Map<String, Expression> = emptyMap(),
    val dialog: Dialog? = null,
)
