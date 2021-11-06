package org.simonolander.lambda.data

import org.simonolander.lambda.Chapter1Level1

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
                        screen = {  }
                    )
                )
            )
        )
    }
}
