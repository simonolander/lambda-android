package org.simonolander.lambda.data

enum class Chapter(
    val id: ChapterId,
    val title: String,
    val levels: List<Level>,
) {
    C1(
        id = ChapterId("tutorial"),
        title = "Tutorial",
        levels = listOf(
            Level.C1L1,
            Level.C1L2,
            Level.C1L3,
            Level.C1L4,
        )
    ),
    C2(
        id = ChapterId("booleans"),
        title = "Booleans",
        levels = listOf()
    ),
    ;

    companion object {
        fun findById(id: ChapterId): Chapter? {
            return values().firstOrNull { it.id == id }
        }
    }
}
