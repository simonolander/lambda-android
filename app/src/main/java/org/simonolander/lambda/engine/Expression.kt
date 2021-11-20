package org.simonolander.lambda.engine

sealed class Expression {
    abstract fun findBoundIdentifiers(index: Int = 0): Sequence<Identifier>
    abstract fun substitute(expression: Expression, index: Int = 0): Expression
    abstract fun incrementFree(increment: Int, threshold: Int = 0): Expression
}

data class Identifier(
    val name: String,
    val index: Int,
) : Expression() {
    override fun findBoundIdentifiers(index: Int): Sequence<Identifier> {
        return if (index == this.index) sequenceOf(this) else emptySequence()
    }

    override fun substitute(expression: Expression, index: Int): Expression {
        return if (index == this.index) {
            expression.incrementFree(index)
        }
        else {
            this
        }
    }

    override fun incrementFree(increment: Int, threshold: Int): Identifier {
        return if (index < threshold) {
            this
        } else {
            Identifier(name, index + increment)
        }
    }
}

data class Function(
    val parameterName: String,
    val body: Expression,
) : Expression() {
    override fun findBoundIdentifiers(index: Int): Sequence<Identifier> {
        return body.findBoundIdentifiers(index + 1)
    }

    override fun substitute(expression: Expression, index: Int): Function {
        return Function(parameterName, body.substitute(expression, index + 1))
    }

    override fun incrementFree(increment: Int, threshold: Int): Function {
        return Function(parameterName, body.incrementFree(increment, threshold + 1))
    }

    fun applyArgument(argument: Expression): Expression {
        return body.substitute(argument)
    }
}

data class Application(
    val function: Expression,
    val argument: Expression,
) : Expression() {
    override fun findBoundIdentifiers(index: Int): Sequence<Identifier> {
        return sequence {
            yieldAll(function.findBoundIdentifiers(index))
            yieldAll(argument.findBoundIdentifiers(index))
        }
    }

    override fun substitute(expression: Expression, index: Int): Expression {
        return Application(
            function = function.substitute(expression, index),
            argument = argument.substitute(expression, index),
        )
    }

    override fun incrementFree(increment: Int, threshold: Int): Expression {
        return Application(
            function = function.incrementFree(increment, threshold),
            argument = argument.incrementFree(increment, threshold),
        )
    }
}
