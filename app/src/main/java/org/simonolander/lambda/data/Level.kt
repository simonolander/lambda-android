package org.simonolander.lambda.data

import org.simonolander.lambda.ui.levels.LevelView

enum class Level(
    val id: LevelId,
    val title: String,
    val view: LevelViewComposable,
) {
    IDENTITY(
        id = LevelId("identity"),
        title = "Identity",
        view = { LevelView(identity, it) }
    ),
    CONSTANT_FUNCTION(
        id = LevelId("const-function"),
        title = "Constant Function",
        view = { LevelView(constantFunctionExercise, it) }
    ),
    TRUE(
        id = LevelId("true"),
        title = "True",
        view = { LevelView(trueExercise, it) }
    ),
    FALSE(
        id = LevelId("false"),
        title = "False",
        view = { LevelView(falseExercise, it) }
    ),
    AND(
        id = LevelId("and"),
        title = "And",
        view = { LevelView(andExercise, it) }
    ),
    ;

    companion object {
        fun findById(id: LevelId): Level? {
            return values().firstOrNull { it.id == id }
        }

        fun nextLevel(id: LevelId): Level? {
            return values()
                .toList()
                .dropWhile { it.id != id }
                .drop(1)
                .firstOrNull()
        }
    }
}
