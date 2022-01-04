package org.simonolander.lambda.data

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
        title = "Basics",
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
    ;

    companion object {
        fun findById(id: ChapterId): Chapter? {
            return values().firstOrNull { it.id == id }
        }
    }
}
