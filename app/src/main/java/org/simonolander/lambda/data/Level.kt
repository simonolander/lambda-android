package org.simonolander.lambda.data

enum class Level(
    val id: LevelId,
    val title: String,
) {
    C1L1(
        id = LevelId("this-is-a-function"),
        title = "This is a function",
    ),
    C1L2(
        id = LevelId("function-decomposition"),
        title = "Parts of a function",
    ),
    C1L3(
        id = LevelId("function-application"),
        title = "Using functions",
    ),
    C1L4(
        id = LevelId("syntactic-sugar"),
        title = "Syntactic sugar",
    ),
    ;

    companion object {
        fun findById(id: LevelId): Level? {
            return values().firstOrNull { it.id == id }
        }
    }
}
