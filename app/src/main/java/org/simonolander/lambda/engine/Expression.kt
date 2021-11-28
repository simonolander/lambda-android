package org.simonolander.lambda.engine

sealed interface Expression {
    val freeVariables: Set<String>

    fun substitute(search: String, replace: Expression): Expression
    fun prettyPrint(): String
}

data class Identifier(val name: String) : Expression {
    override val freeVariables = setOf(name)

    override fun substitute(search: String, replace: Expression): Expression {
        return when (search) {
            name -> replace
            else -> this
        }
    }

    override fun prettyPrint(): String {
        return name
    }

    override fun toString(): String {
        return name
    }
}

data class Function(
    val parameterName: String,
    val body: Expression,
) : Expression {
    override val freeVariables by lazy {
        body.freeVariables - parameterName
    }

    /**
     * Creates a new [Function] with [parameterName] and all free occurrences of [parameterName] in
     * the [body] changed to [newParameterName]. No care is taken to prevent the new parameter name
     * from unintentionally binding to a free variable in [body].
     *
     * @return the new function, or this function if the [parameterName] did not change
     */
    fun unsafeRenameParameter(newParameterName: String): Function {
        return if (newParameterName == parameterName) {
            this
        } else {
            Function(newParameterName, body.substitute(
                parameterName,
                Identifier(newParameterName),
            ))
        }
    }

    override fun substitute(search: String, replace: Expression): Function {
        return when (search) {
            parameterName -> this
            else -> Function(parameterName, body.substitute(search, replace))
        }
    }

    override fun prettyPrint(): String {
        return "λ $parameterName. ${body.prettyPrint()}"
    }

    override fun toString(): String {
        return "λ $parameterName. ($body)"
    }
}

data class Application(
    val function: Expression,
    val argument: Expression,
) : Expression {
    override val freeVariables by lazy {
        function.freeVariables + argument.freeVariables
    }

    override fun substitute(search: String, replace: Expression): Expression {
        return Application(
            function.substitute(search, replace),
            argument.substitute(search, replace),
        )
    }

    override fun prettyPrint(): String {
        val f = when (function) {
            is Function -> "(${function.prettyPrint()})"
            is Application ->
                when (function.argument) {
                    is Function -> "(${function.prettyPrint()})"
                    else -> function.prettyPrint()
                }
            else -> function.prettyPrint()
        }
        val a = when (argument) {
            is Application -> "(${argument.prettyPrint()})"
            else -> argument.prettyPrint()
        }
        return "$f $a"
    }

    override fun toString(): String {
        return "($function) ($argument)"
    }
}
