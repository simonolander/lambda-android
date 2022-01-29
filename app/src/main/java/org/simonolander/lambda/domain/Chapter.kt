package org.simonolander.lambda.domain

enum class Chapter(
    val id: ChapterId,
    val title: String,
    val levels: List<Level>,
) {
    INTRODUCTION(
        id = ChapterId("introduction"),
        title = "Introduction",
        levels = listOf(
            Level.HELLO,
            Level.WHAT_IS_LAMBDA_CALCULUS,
            Level.APPLICATION_SYNTAX,
            Level.FUNCTION_SYNTAX,
        )
    ),
    BASIC_EXERCISES(
        id = ChapterId("basic-exercises"),
        title = "Basic functions",
        levels = listOf(
            Level.IDENTITY,
            Level.CONSTANT_FUNCTION,
            Level.KESTREL,
            Level.KITE,
            Level.APPLICATOR,
            Level.CARDINAL,
        )
    ),
    BOOLEAN_LOGIC(
        id = ChapterId("boolean-logic"),
        title = "Booleans",
        levels = listOf(
            Level.TRUE,
            Level.FALSE,
            Level.NOT,
            Level.AND,
            Level.OR,
            Level.IF_THEN_ELSE,
            Level.EXCLUSIVE_OR,
        )
    ),
    NATURAL_NUMBERS(
        id = ChapterId("church-numerals"),
        title = "Natural numbers",
        levels = listOf(
            Level.NATURAL_NUMBERS,
            Level.SUCCESSOR,
            Level.ADDITION,
            Level.MULTIPLICATION,
            Level.EXPONENTIATION,
            Level.IS_ZERO,
            Level.IS_EVEN,
            Level.PREDECESSOR,
            Level.SUBTRACTION,
            Level.LESS_THAN_OR_EQUAL,
            Level.EQUALS,
        )
    ),
    PAIRS(
        id = ChapterId("pairs"),
        title = "Pairs",
        levels = listOf(
            Level.PAIRS,
            Level.FIRST,
            Level.SECOND,
        )
    ),
    ;

    companion object {
        fun findById(id: ChapterId): Chapter? {
            return values().firstOrNull { it.id == id }
        }
    }
}
