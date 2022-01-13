package org.simonolander.lambda.domain

import org.simonolander.lambda.content.dialog.applicationSyntaxDialog
import org.simonolander.lambda.content.dialog.functionSyntaxDialog
import org.simonolander.lambda.content.dialog.helloDialog
import org.simonolander.lambda.content.dialog.numbers.naturalNumbersDialog
import org.simonolander.lambda.content.dialog.whatIsLambdaCalculusDialog
import org.simonolander.lambda.content.exercise.*
import org.simonolander.lambda.misc.lambdaCalculus
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
        view = { SimpleDialogLevelView(helloDialog, it) },
    ),
    WHAT_IS_LAMBDA_CALCULUS(
        id = LevelId("what-is-lambda-calculus"),
        title = "What is $lambdaCalculus",
        view = { SimpleDialogLevelView(whatIsLambdaCalculusDialog, it) },
    ),
    APPLICATION_SYNTAX(
        id = LevelId("application-syntax"),
        title = "Application Syntax",
        view = { SimpleDialogLevelView(applicationSyntaxDialog, it) },
    ),
    FUNCTION_SYNTAX(
        id = LevelId("function-syntax"),
        title = "Function Syntax",
        view = { SimpleDialogLevelView(functionSyntaxDialog, it) },
    ),
    IDENTITY(
        id = LevelId("identity"),
        title = "Identity",
        view = { LevelView(identityExercise, it) },
    ),
    CONSTANT_FUNCTION(
        id = LevelId("const-function"),
        title = "Constant function",
        view = { LevelView(constantExercise, it) },
    ),
    KESTREL(
        id = LevelId("kestrel"),
        title = "Const",
        view = { LevelView(kestrelExercise, it) },
    ),
    KITE(
        id = LevelId("kite"),
        title = "Kite",
        view = { LevelView(kiteExercise, it) },
    ),
    APPLICATOR(
        id = LevelId("applicator"),
        title = "Apply",
        view = { LevelView(applicatorExercise, it) },
    ),
    CARDINAL(
        id = LevelId("cardinal"),
        title = "Flip",
        view = { LevelView(cardinalExercise, it) },
    ),
    TRUE(
        id = LevelId("true"),
        title = "True",
        view = { LevelView(trueExercise, it) },
    ),
    FALSE(
        id = LevelId("false"),
        title = "False",
        view = { LevelView(falseExercise, it) },
    ),
    NOT(
        id = LevelId("not"),
        title = "Not",
        view = { LevelView(notExercise, it) },
    ),
    AND(
        id = LevelId("and"),
        title = "And",
        view = { LevelView(andExercise, it) },
    ),
    OR(
        id = LevelId("or"),
        title = "Or",
        view = { LevelView(orExercise, it) },
    ),
    IF_THEN_ELSE(
        id = LevelId("if"),
        title = "If-then-else",
        view = { LevelView(ifExercise, it) },
    ),
    EXCLUSIVE_OR(
        id = LevelId("xor"),
        title = "Exclusive Or",
        view = { LevelView(xorExercise, it) },
    ),
    NATURAL_NUMBERS(
        id = LevelId("natural-numbers"),
        title = "Natural numbers",
        view = { SimpleDialogLevelView(naturalNumbersDialog, it) },
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
