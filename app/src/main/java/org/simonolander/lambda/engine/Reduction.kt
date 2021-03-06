package org.simonolander.lambda.engine

sealed interface Reduction {
    val before: Expression
    val after: Expression
    val root: Reduction

    fun prettyPrint(): String {
        return "{$before -> $after}"
    }
}

data class EtaReduction(
    override val before: Expression,
    override val after: Expression,
) : Reduction {
    override val root = this
}

data class BetaReduction(
    override val before: Expression,
    override val after: Expression,
) : Reduction {
    override val root = this
}

data class LibraryReduction(
    override val before: Expression,
    override val after: Expression,
) : Reduction {
    override val root = this
}

data class FunctionBodyReduction(
    val parameterName: String,
    val reduction: Reduction,
) : Reduction {
    override val before = Function(parameterName, reduction.before)
    override val after = Function(parameterName, reduction.after)
    override val root by lazy { reduction.root }

    override fun prettyPrint(): String {
        return "λ $parameterName. ${reduction.prettyPrint()}"
    }
}

data class ApplicationFunctionReduction(
    val functionReduction: Reduction,
    val argument: Expression,
) : Reduction {
    override val before = Application(functionReduction.before, argument)
    override val after = Application(functionReduction.after, argument)
    override val root by lazy { functionReduction.root }

    override fun prettyPrint(): String {
        return "(${functionReduction.prettyPrint()}) ($argument)"
    }
}

data class ApplicationArgumentReduction(
    val function: Expression,
    val argumentReduction: Reduction,
) : Reduction {
    override val before = Application(function, argumentReduction.before)
    override val after = Application(function, argumentReduction.after)
    override val root by lazy { argumentReduction.root }

    override fun prettyPrint(): String {
        return "($function) (${argumentReduction.prettyPrint()})"
    }
}

data class AlphaRenaming(
    override val before: Function,
    override var after: Function,
) : Reduction {
    override val root = this
}
