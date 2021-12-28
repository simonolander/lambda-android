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
            Level.IDENTITY,
            Level.CONSTANT_FUNCTION,
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
