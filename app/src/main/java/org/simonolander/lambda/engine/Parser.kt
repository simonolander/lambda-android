package org.simonolander.lambda.engine

import java.util.*

enum class TokenType {
    LeftParenthesis,
    RightParenthesis,
    Identifier,
    Lambda,
    Dot,
}

data class Token(val text: String, val type: TokenType, val range: IntRange)

/**
 * @return The parsed [Expression]
 * @throws ParserException If there's a syntax error in the code
 */
fun parse(code: String): Expression {
    return expression(LinkedList(tokenize(code))).toExpression()
}

private fun expression(tokens: Queue<Token>): CSTExpression {
    return when (tokens.peek()?.type) {
        TokenType.Lambda -> function(tokens)
        TokenType.LeftParenthesis -> application(tokens)
        TokenType.Identifier -> application(tokens)
        TokenType.Dot -> throw ParserException("Expected either a λ, a left parenthesis, or an identifier, but found a dot")
        TokenType.RightParenthesis -> throw ParserException("Expected either a λ, a left parenthesis, or an identifier, but found a right parenthesis")
        null -> throw ParserException("Expected either a λ, a left parenthesis, or an identifier, but there's no more expression")
    }
}

private fun function(tokens: Queue<Token>): CSTFunction {
    val lambda = tokens.poll() ?: throw ParserException("Expected a λ, but there's no more expression")
    if (lambda.type != TokenType.Lambda) {
        throw ParserException("Expected a λ, but found $lambda")
    }
    val parameters = mutableListOf<CSTIdentifier>()
    while (true) {
        val identifierOrDot = tokens.poll() ?: throw ParserException("Expected either an identifier or a dot, but there's no more expression")
        when (identifierOrDot.type) {
            TokenType.Identifier -> parameters += CSTIdentifier(identifierOrDot.text)
            TokenType.Dot -> break
            else -> throw ParserException("Expected either an identifier or a dot, but found $identifierOrDot")
        }
    }
    if (parameters.isEmpty()) {
        throw throw ParserException("The function needs at least one parameter")
    }
    val body = expression(tokens)
    return CSTFunction(parameters, body)
}

private fun application(tokens: Queue<Token>): CSTExpression {
    val expressions = mutableListOf<CSTExpression>()
    while (true) {
        val token = tokens.peek() ?: break
        when (token.type) {
            TokenType.LeftParenthesis -> expressions += parenthesizedExpression(tokens)
            TokenType.RightParenthesis -> break
            TokenType.Identifier -> expressions += identifier(tokens)
            TokenType.Lambda -> expressions += function(tokens)
            TokenType.Dot -> throw ParserException("Expected a left or right parenthesis, a λ, or an identifier, but found a dot")
        }
    }
    if (expressions.isEmpty()) {
        throw ParserException("Expected at least one expression in the application")
    }
    return expressions.reduceOrNull { acc, expr -> CSTApplication(acc, expr) }
        ?: throw ParserException("Expected at least one expression in the application")
}

private fun identifier(tokens: Queue<Token>): CSTIdentifier {
    val identifier = tokens.poll() ?: throw ParserException("Expected an identifier, but there's no more expression")
    if (identifier.type != TokenType.Identifier) {
        throw throw ParserException("Expected an identifier, but found $identifier")
    }
    return CSTIdentifier(identifier.text)
}

private fun parenthesizedExpression(tokens: Queue<Token>): CSTParenthesizedExpression {
    val leftParenthesis = tokens.poll() ?: throw ParserException("Expected a left parenthesis, but there's no more expression")
    if (leftParenthesis.type != TokenType.LeftParenthesis) {
        throw ParserException("Expected a left parenthesis, but found $leftParenthesis")
    }
    val expr = expression(tokens)
    val rightParenthesis = tokens.poll() ?: throw ParserException("Expected a right parenthesis, but there's no more expression")
    if (rightParenthesis.type != TokenType.RightParenthesis) {
        throw ParserException("Expected a right parenthesis, but found $leftParenthesis")
    }
    return CSTParenthesizedExpression(expr)
}

private fun tokenize(code: String): List<Token> {
    val tokens = mutableListOf<Token>()
    val buffer = StringBuilder()
    for ((index, character) in code.withIndex()) {
        if (character.isIdentifier()) {
            buffer.append(character)
            continue
        }
        if (buffer.isNotEmpty()) {
            tokens += Token(
                buffer.toString(),
                TokenType.Identifier,
                IntRange(index - buffer.length, index - 1)
            )
            buffer.clear()
        }
        when {
            character.isLambda() -> tokens += Token(
                character.toString(),
                TokenType.Lambda,
                IntRange(index, index)
            )
            character.isLeftParenthesis() -> tokens += Token(
                character.toString(),
                TokenType.LeftParenthesis,
                IntRange(index, index)
            )
            character.isRightParenthesis() -> tokens += Token(
                character.toString(),
                TokenType.RightParenthesis,
                IntRange(index, index)
            )
            character.isDot() -> tokens += Token(
                character.toString(),
                TokenType.Dot,
                IntRange(index, index)
            )
        }
    }
    if (buffer.isNotEmpty()) {
        tokens += Token(
            buffer.toString(),
            TokenType.Identifier,
            IntRange(code.length - buffer.length, code.length - 1)
        )
        buffer.clear()
    }
    return tokens
}

fun Char.isIdentifier(): Boolean {
    return !isLambda() && isLetterOrDigit()
}

fun Char.isLeftParenthesis(): Boolean {
    return this == '('
}

fun Char.isRightParenthesis(): Boolean {
    return this == ')'
}

fun Char.isLambda(): Boolean {
    return this == 'λ' || this == '\\'
}

fun Char.isDot(): Boolean {
    return this == '.'
}
