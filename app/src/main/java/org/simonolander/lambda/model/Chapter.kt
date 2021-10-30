package org.simonolander.lambda.model

data class Chapter(
    val id: ChapterId,
    val name: String,
    val levels: List<Level>,
)

@JvmInline
value class ChapterId(val value: String)

private val chapterTutorial = Chapter(
    ChapterId("tut"),
    "Tutorial",
    listOf(
        Level(LevelId("this-is-a-function"), "This is a function"),
        Level(LevelId("function-decomposition"), "Function decomposition"),
        Level(LevelId("function-application"), "Function application"),
        Level(LevelId("sugar"), "Sugar"),
    ),
)

val chapters = listOf(
    chapterTutorial
)
