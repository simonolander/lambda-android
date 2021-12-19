package org.simonolander.lambda.data

import androidx.compose.ui.text.AnnotatedString

/**
 * An exercise represents a problem to which the user must supply a program
 * that given the parameters of the exercise produces the correct output.
 */
data class Exercise(
    val name: String,
    val description: AnnotatedString,
    val functionName: String,
    val testCases: List<TestCase>,
) {
    constructor(
        name: String,
        description: String,
        functionName: String,
        testCases: List<TestCase>,
    ): this(
        name,
        AnnotatedString(description),
        functionName,
        testCases,
    )
}
