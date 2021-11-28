package org.simonolander.lambda.engine

fun reduceAll(
    expression: Expression,
    library: Map<String, Expression> = emptyMap(),
    maxDepth: Int = 1000
): Expression {
    return reduceAll(expression, library)
        .map { it.after }
        .ifEmpty { sequenceOf(expression) }
        .take(maxDepth)
        .last()
}

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
 *     (λ x. x) a
 * to
 *     a
 */
private fun betaReduce(application: Application): Reduction? {
    val (function, argument) = application
    if (function !is Function) {
        return null
    }
    val (parameterName, body) = function
    val alphaRenaming = alphaRename(parameterName, argument, body)
    if (alphaRenaming != null) {
        return ApplicationFunctionReduction(
            FunctionBodyReduction(parameterName, alphaRenaming),
            argument,
        )
    }
    return BetaReduction(
        before = application,
        after = body.substitute(parameterName, argument),
    )
}

/**
 * When reducing expressions such as `(λ x y. x) y` we cannot just substitute `y` into `λ y. y`,
 * since that would yield `λ y. y`, whereas the correct reduction should be `λ a. y`.
 *
 * This function identifies and performs alpha renaming, in the above example from `y` to `a`,
 * so that the substitution can proceed without unintentionally changing the free variable `y` into
 * a bound variable.
 *
 * @param search the free variable that is being replaced, in the above example `x`
 * @param replace the expression that the free variable is being replaced by, in the above example `y`
 * @param subject the expression in which we search for conflicts, in the above example `λ x y. x`
 * @return the first alpha renaming that is identified, or `null` if none is found
 */
private fun alphaRename(search: String, replace: Expression, subject: Expression): Reduction? {
    if (search !in subject.freeVariables) {
        return null
    }
    return when (subject) {
        is Application -> {
            val (function, argument) = subject
            val functionReduction = alphaRename(search, replace, function)
            if (functionReduction != null) {
                ApplicationFunctionReduction(functionReduction, argument)
            } else {
                alphaRename(search, replace, argument)?.let {
                    ApplicationArgumentReduction(function, it)
                }
            }
        }
        is Function -> {
            val (parameterName, body) = subject
            if (parameterName in replace.freeVariables) {
                val newParameterName = nextName(subject.freeVariables + replace.freeVariables)
                AlphaRenaming(subject, subject.unsafeRenameParameter(newParameterName))
            } else {
                alphaRename(search, replace, body)?.let {
                    FunctionBodyReduction(parameterName, it)
                }
            }
        }
        is Identifier -> null
    }
}

/**
 * Eta reductions reduces expressions of the form
 *     λ x. a x
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
    while (candidate.toString().reversed() in names) {
        candidate++
    }
    return candidate.toString().reversed()
}

private operator fun StringBuilder.inc(): StringBuilder {
    for (index in 0 until length) {
        val newChar = when (val oldChar = this[index]) {
            'z' -> 'a'
            else -> oldChar + 1
        }
        setCharAt(index, newChar)
        if (newChar != 'a') {
            return this
        }
    }
    return append('a')
}

