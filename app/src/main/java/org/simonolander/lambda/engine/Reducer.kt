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

private fun betaReduce(application: Application): BetaReduction? {
    val (function, argument) = application
    if (function !is Function) {
        return null
    }
    return BetaReduction(
        before = application,
        after = function.applyArgument(argument),
    )
}

private fun etaReduce(function: Function): EtaReduction? {
    if (function.body !is Application) {
        return null
    }
    if (function.body.argument !is Identifier) {
        return null
    }
    if (function.body.argument.index != 0) {
        return null
    }
    if (function.body.function.findBoundIdentifiers().any()) {
        return null
    }
    return EtaReduction(
        before = function,
        after = function.body.function,
    )
}

