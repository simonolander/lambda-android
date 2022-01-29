package org.simonolander.lambda.engine

/**
 * A De-Bruijn expression is similar to the regular expression, except that the variables are
 * indexed.
 *
 * See [https://en.wikipedia.org/wiki/De_Bruijn_index]
 */
sealed interface DeBruijnExpression {
    /**
     * Binds the identifier [name] at index [index], meaning that if the expressions contains the
     * free variable [name], it gets replaced by a bound identifier. The index counts how many
     * functions you need to "get out of" before finding the binding function.
     */
    fun bind(name: String, index: Int = 0): DeBruijnExpression
}

sealed interface DeBruijnIdentifier : DeBruijnExpression

data class BoundIdentifier(
    val index: Int,
) : DeBruijnIdentifier {
    override fun bind(name: String, index: Int): BoundIdentifier {
        return this
    }
}

data class FreeIdentifier(
    val name: String,
) : DeBruijnIdentifier {
    override fun bind(name: String, index: Int): DeBruijnIdentifier {
        return if (this.name == name) {
            BoundIdentifier(index)
        }
        else {
            this
        }
    }
}

data class DeBruijnFunction(
    val body: DeBruijnExpression,
) : DeBruijnExpression {
    override fun bind(name: String, index: Int): DeBruijnFunction {
        return DeBruijnFunction(body.bind(name, index + 1))
    }
}

data class DeBruijnApplication(
    val function: DeBruijnExpression,
    val argument: DeBruijnExpression,
) : DeBruijnExpression {
    override fun bind(name: String, index: Int): DeBruijnApplication {
        return DeBruijnApplication(function.bind(name, index), argument.bind(name, index))
    }
}
