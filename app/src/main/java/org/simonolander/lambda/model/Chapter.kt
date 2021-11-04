package org.simonolander.lambda.model

import org.simonolander.lambda.R

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
        Level(LevelId("this-is-a-function"), "This is a function", R.id.c1L1Fragment),
        Level(LevelId("function-decomposition"), "Function decomposition", R.id.c1L2Fragment),
        Level(LevelId("function-application"), "Function application", 0),
        Level(LevelId("sugar"), "Sugar", 0),
    ),
)

val chapters = listOf(
    chapterTutorial
)
