package org.simonolander.lambda.engine

sealed class Expression

data class Identifier(
    val name: String,
    val index: Long,
) : Expression()

data class Function(
    val parameterName: String,
    val body: Expression,
) : Expression()

data class Application(
    val function: Expression,
    val argument: Expression,
): Expression()
