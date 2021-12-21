package org.simonolander.lambda.data

import org.simonolander.lambda.engine.Expression

data class TestCase(
    val input: Expression,
    val output: Expression,
)
