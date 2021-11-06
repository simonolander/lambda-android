package org.simonolander.lambda.data

data class Chapter(
    val id: ChapterId,
    val name: String,
    val levels: List<Level>,
) {
    companion object {
        val values = listOf(
            Chapter(
                id = ChapterId("tutorial"),
                name = "Tutorial",
                levels = listOf(
                    Level(
                        id = LevelId("this-is-a-function"),
                        name = "This is a function",
                    ),
                    Level(
                        id = LevelId("function-decomposition"),
                        name = "Parts of a function",
                    ),
                    Level(
                        id = LevelId("function-application"),
                        name = "Using functions",
                    ),
                    Level(
                        id = LevelId("sugar"),
                        name = "Syntactic sugar",
                    )
                )
            )
        )

        fun fromRoute(route: String): Level? {
            val segments = route.split("/")
            return if (segments.size == 2) {
                val chapterId = ChapterId(segments[0])
                val levelId = LevelId(segments[1])
                values.firstOrNull { it.id == chapterId }?.levels?.firstOrNull { it.id == levelId }
            } else {
                null
            }
        }
    }
}
