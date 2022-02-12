package org.simonolander.lambda.content

import org.simonolander.lambda.content.dialog.intro.*
import org.simonolander.lambda.content.dialog.numbers.naturalNumbersDialog
import org.simonolander.lambda.content.dialog.pair.pairsDialog
import org.simonolander.lambda.content.exercise.basic.*
import org.simonolander.lambda.content.exercise.booleans.*
import org.simonolander.lambda.content.exercise.numbers.*
import org.simonolander.lambda.content.exercise.pairs.firstExercise
import org.simonolander.lambda.content.exercise.pairs.secondExercise
import org.simonolander.lambda.domain.LevelId
import org.simonolander.lambda.domain.LevelViewComposable
import org.simonolander.lambda.ui.levels.LevelView
import org.simonolander.lambda.ui.view.SimpleDialogLevelView

enum class Level(
    val id: LevelId,
    val title: String,
) {
    HELLO(
        id = LevelId("hello"),
        title = "Hello",
    ),
    FUNCTIONS_AND_APPLICATIONS(
        id = LevelId("functions"),
        title = "Functions and applications",
    ),
    IDENTITY(
        id = LevelId("identity"),
        title = "Identity",
    ),
    CONSTANT_FUNCTION(
        id = LevelId("const-function"),
        title = "Constant function",
    ),
    CURRYING(
        id = LevelId("currying"),
        title = "Currying",
    ),
    KESTREL(
        id = LevelId("kestrel"),
        title = "Const",
    ),
    KITE(
        id = LevelId("kite"),
        title = "Kite",
    ),
    APPLICATION_SYNTAX(
        id = LevelId("application-syntax"),
        title = "Application Syntax",
    ),
    APPLICATOR(
        id = LevelId("applicator"),
        title = "Apply",
    ),
    CARDINAL(
        id = LevelId("cardinal"),
        title = "Flip",
    ),
    FUNCTION_SYNTAX(
        id = LevelId("function-syntax"),
        title = "Function Syntax",
    ),
    TRUE(
        id = LevelId("true"),
        title = "True",
    ),
    FALSE(
        id = LevelId("false"),
        title = "False",
    ),
    NOT(
        id = LevelId("not"),
        title = "Not",
    ),
    AND(
        id = LevelId("and"),
        title = "And",
    ),
    OR(
        id = LevelId("or"),
        title = "Or",
    ),
    IF_THEN_ELSE(
        id = LevelId("if"),
        title = "If-then-else",
    ),
    EXCLUSIVE_OR(
        id = LevelId("xor"),
        title = "Exclusive Or",
    ),
    NATURAL_NUMBERS(
        id = LevelId("natural-numbers"),
        title = "Natural numbers",
    ),
    SUCCESSOR(
        id = LevelId("successor"),
        title = "Successor",
    ),
    ADDITION(
        id = LevelId("addition"),
        title = "Addition",
    ),
    MULTIPLICATION(
        id = LevelId("multiplication"),
        title = "Multiplication",
    ),
    EXPONENTIATION(
        id = LevelId("exponentiation"),
        title = "Exponentiation",
    ),
    IS_ZERO(
        id = LevelId("is-zero"),
        title = "Zero",
    ),
    IS_EVEN(
        id = LevelId("is-even"),
        title = "Even",
    ),
    PREDECESSOR(
        id = LevelId("predecessor"),
        title = "Predecessor",
    ),
    SUBTRACTION(
        id = LevelId("subtraction"),
        title = "Subtraction",
    ),
    LESS_THAN_OR_EQUAL(
        id = LevelId("less-than-or-equal"),
        title = "Less than or equal",
    ),
    EQUALS(
        id = LevelId("equals"),
        title = "Equals",
    ),
    PAIRS(
        id = LevelId("pairs"),
        title = "Defining the pair",
    ),
    FIRST(
        id = LevelId("fst"),
        title = "First",
    ),
    SECOND(
        id = LevelId("snd"),
        title = "Second",
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

        fun isLastInChapter(id: LevelId): Boolean {
            return Chapter.values()
                .mapNotNull { it.levels.lastOrNull() }
                .any { it.id == id }
        }
    }
}
