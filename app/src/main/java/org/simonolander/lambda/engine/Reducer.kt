package org.simonolander.lambda.engine

fun reduceAll(
    expression: Expression,
    library: Map<String, Expression> = emptyMap()
): Sequence<Reduction> {
    return generateSequence(
        seedFunction = { reduceOnce(expression, library) },
        nextFunction = { reduceOnce(it.after, library) }
    )
}

fun reduceOnce(expression: Expression, library: Map<String, Expression> = emptyMap()): Reduction? {
    return when (expression) {
        is Identifier -> reduceOnce(expression, library)
        is Function -> reduceOnce(expression, library)
        is Application -> reduceOnce(expression, library)
    }
}

private fun reduceOnce(identifier: Identifier, library: Map<String, Expression>): Reduction? {
    return library[identifier.name]
        ?.let {
            LibraryReduction(
                before = identifier,
                after = it
            )
        }
}

private fun reduceOnce(function: Function, library: Map<String, Expression>): Reduction? {
    val (parameterName, body) = function
    return etaReduce(function)
        ?: reduceOnce(body, library)?.let { FunctionBodyReduction(parameterName, it) }
}

private fun reduceOnce(application: Application, library: Map<String, Expression>): Reduction? {
    val (function, argument) = application
    return betaReduce(application)
        ?: reduceOnce(function, library)?.let { ApplicationFunctionReduction(it, argument) }
        ?: reduceOnce(argument, library)?.let { ApplicationArgumentReduction(function, it) }
}

/**
 * Reduces expressions of the form
 *     (\ x. x) a
 * to
 *     a
 */
private fun betaReduce(application: Application): Reduction? {
    val (function, argument) = application
    if (function !is Function) {
        return null
    }
    return if (function.parameterName in argument.freeVariables) {
        val newParameterName = nextName(argument.freeVariables + function.body.freeVariables)
        ApplicationFunctionReduction(
            AlphaRenaming(
                function,
                Function(
                    newParameterName,
                    function.body.substitute(function.parameterName, Identifier(newParameterName)),
                ),
            ),
            argument,
        )
    }
    else {
        BetaReduction(
            before = application,
            after = function.body.substitute(function.parameterName, argument),
        )
    }

}

/**
 * Eta reductions reduces expressions of the form
 *     \ x. a x
 * to
 *     a
 */
private fun etaReduce(function: Function): EtaReduction? {
    if (function.body !is Application) {
        return null
    }
    if (function.body.argument !is Identifier) {
        return null
    }
    if (function.body.argument.name != function.parameterName) {
        return null
    }
    // TODO What about library?
    if (function.body.function.freeVariables.contains(function.parameterName)) {
        return null
    }
    return EtaReduction(
        before = function,
        after = function.body.function,
    )
}

private fun nextName(names: Set<String>): String {
    var candidate = StringBuilder("a")
    while (candidate.toString() in names) {
        candidate++
    }
    return candidate.toString()
}

private operator fun StringBuilder.inc(): StringBuilder {
    for (index in length.rangeTo(0)) {
        val newChar = when (val oldChar = this[index]) {
            'z' -> 'a'
            else -> oldChar + 1
        }
        setCharAt(index, newChar)
        if (newChar != 'a') {
            return this
        }
    }
    return insert(0, 'a')
}

