package org.simonolander.lambda.data

import org.simonolander.lambda.content.Dialog.helloDialog
import org.simonolander.lambda.content.Dialog.applicationSyntaxDialog
import org.simonolander.lambda.content.Dialog.whatAreFunctionsDialog
import org.simonolander.lambda.ui.levels.LevelView
import org.simonolander.lambda.ui.view.SimpleDialogLevelView

enum class Level(
    val id: LevelId,
    val title: String,
    val view: LevelViewComposable,
) {
    HELLO(
        id = LevelId("hello"),
        title = "Hello",
        view = { SimpleDialogLevelView(helloDialog, it) }
    ),
    WHAT_ARE_FUNCTIONS(
        id = LevelId("what-are-functions"),
        title = "What's a function",
        view = { SimpleDialogLevelView(whatAreFunctionsDialog, it) }
    ),
    SYNTAX(
        id = LevelId("syntax"),
        title = "Syntax",
        view = { SimpleDialogLevelView(applicationSyntaxDialog, it) }
    ),
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
    NOT(
        id = LevelId("not"),
        title = "Not",
        view = { LevelView(notExercise, it) }
    ),
    AND(
        id = LevelId("and"),
        title = "And",
        view = { LevelView(andExercise, it) }
    ),
    OR(
        id = LevelId("or"),
        title = "Or",
        view = { LevelView(orExercise, it) }
    ),
    IF_THEN_ELSE(
        id = LevelId("if"),
        title = "If-then-else",
        view = { LevelView(ifExercise, it) }
    ),
    EXCLUSIVE_OR(
        id = LevelId("xor"),
        title = "Exclusive Or",
        view = { LevelView(xorExercise, it) }
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
