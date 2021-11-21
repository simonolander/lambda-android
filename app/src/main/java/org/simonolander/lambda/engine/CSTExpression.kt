package org.simonolander.lambda.engine

import java.lang.IllegalArgumentException

/**
 * Grammar
 *
 * Expression ::= Identifier
 *  | Function
 *  | Application
 *  | ParenthesizedExpression
 *
 * Function ::= LAMBDA Identifier+ DOT Expression
 *
 * Application ::= Expression Expression
 *
 * ParenthesizedExpression :: LEFT_PARENTHESIS Expression RIGHT_PARENTHESIS
 */
sealed class CSTExpression {
    fun toExpression(): Expression {
        return toExpression(emptyList())
    }

    abstract fun toExpression(binders: List<String>): Expression
}

data class CSTIdentifier(
    val name: String,
) : CSTExpression() {
    override fun toExpression(binders: List<String>): Expression {
        val index = binders.reversed()
            .indexOfFirst { it == name }
            .takeUnless { it < 0 }
            ?: binders.size
        return Identifier(name, index)
    }
}

data class CSTFunction(
    val parameters: List<CSTIdentifier>,
    val body: CSTExpression,
) : CSTExpression() {
    override fun toExpression(binders: List<String>): Expression {
        val name = parameters.firstOrNull()?.name ?: return body.toExpression(binders)
        val body = CSTFunction(parameters.drop(1), body).toExpression(binders + name)
        return Function(name, body)
    }
}

data class CSTApplication(
    val function: CSTExpression,
    val argument: CSTExpression,
) : CSTExpression() {
    override fun toExpression(binders: List<String>): Expression {
        return Application(function.toExpression(binders), argument.toExpression(binders))
    }
}

data class CSTParenthesizedExpression(
    val expression: CSTExpression,
) : CSTExpression() {
    override fun toExpression(binders: List<String>): Expression {
        return expression.toExpression(binders)
    }
}
