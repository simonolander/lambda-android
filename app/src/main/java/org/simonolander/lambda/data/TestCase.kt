package org.simonolander.lambda.data

import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.engine.parse

data class TestCase(
    val input: Expression,
    val output: Expression,
) {
    constructor(input: String, output: String) : this(parse(input), parse(output))
    constructor(inputOutputPair: Pair<String, String>) : this(inputOutputPair.first, inputOutputPair.second)
}
