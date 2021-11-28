package org.simonolander.lambda.engine

sealed interface Reduction {
    val before: Expression
    val after: Expression
}

data class EtaReduction(override val before: Expression, override val after: Expression): Reduction

data class BetaReduction(override val before: Expression, override val after: Expression): Reduction

data class LibraryReduction(override val before: Expression, override val after: Expression): Reduction

data class FunctionBodyReduction(val parameterName: String, val reduction: Reduction): Reduction {
    override val before = Function(parameterName, reduction.before)
    override val after = Function(parameterName, reduction.after)
}

data class ApplicationFunctionReduction(val functionReduction: Reduction, val argument: Expression): Reduction {
    override val before = Application(functionReduction.before, argument)
    override val after = Application(functionReduction.after, argument)
}

data class ApplicationArgumentReduction(val function: Expression, val argumentReduction: Reduction): Reduction {
    override val before = Application(function, argumentReduction.before)
    override val after = Application(function, argumentReduction.after)
}

data class AlphaRenaming(override val before: Function, override var after: Function): Reduction
