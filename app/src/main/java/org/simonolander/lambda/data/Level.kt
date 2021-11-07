package org.simonolander.lambda.data

import org.simonolander.lambda.ui.levels.C1L1
import org.simonolander.lambda.ui.levels.C1L2
import org.simonolander.lambda.ui.levels.C1L3
import org.simonolander.lambda.ui.levels.C1L4

enum class Level(
    val id: LevelId,
    val title: String,
    val view: LevelViewComposable
) {
    C1L1(
        id = LevelId("this-is-a-function"),
        title = "This is a function",
        view = { C1L1(it) }
    ),
    C1L2(
        id = LevelId("function-decomposition"),
        title = "Parts of a function",
        view = { C1L2(it) }
    ),
    C1L3(
        id = LevelId("function-application"),
        title = "Using functions",
        view = { C1L3(it) }
    ),
    C1L4(
        id = LevelId("syntactic-sugar"),
        title = "Syntactic sugar",
        view = { C1L4(it) }
    ),
    ;

    companion object {
        fun findById(id: LevelId): Level? {
            return values().firstOrNull { it.id == id }
        }
    }
}
