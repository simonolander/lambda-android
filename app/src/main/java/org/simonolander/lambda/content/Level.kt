package org.simonolander.lambda.content

import org.simonolander.lambda.content.dialog.intro.applicationSyntaxDialog
import org.simonolander.lambda.content.dialog.intro.curryingDialog
import org.simonolander.lambda.content.dialog.intro.functionSyntaxDialog
import org.simonolander.lambda.content.dialog.intro.functionsAndApplicationsDialog
import org.simonolander.lambda.content.dialog.intro.helloDialog
import org.simonolander.lambda.content.dialog.numbers.naturalNumbersDialog
import org.simonolander.lambda.content.dialog.pair.pairsDialog
import org.simonolander.lambda.content.exercise.basic.applicatorExercise
import org.simonolander.lambda.content.exercise.basic.cardinalExercise
import org.simonolander.lambda.content.exercise.basic.constantExercise
import org.simonolander.lambda.content.exercise.basic.identityExercise
import org.simonolander.lambda.content.exercise.basic.kestrelExercise
import org.simonolander.lambda.content.exercise.basic.kiteExercise
import org.simonolander.lambda.content.exercise.booleans.andExercise
import org.simonolander.lambda.content.exercise.booleans.falseExercise
import org.simonolander.lambda.content.exercise.booleans.ifThenElseExercise
import org.simonolander.lambda.content.exercise.booleans.notExercise
import org.simonolander.lambda.content.exercise.booleans.orExercise
import org.simonolander.lambda.content.exercise.booleans.trueExercise
import org.simonolander.lambda.content.exercise.booleans.xorExercise
import org.simonolander.lambda.content.exercise.numbers.additionExercise
import org.simonolander.lambda.content.exercise.numbers.equalsExercise
import org.simonolander.lambda.content.exercise.numbers.evenExercise
import org.simonolander.lambda.content.exercise.numbers.exponentiationExercise
import org.simonolander.lambda.content.exercise.numbers.lessThanOrEqualExercise
import org.simonolander.lambda.content.exercise.numbers.multiplicationExercise
import org.simonolander.lambda.content.exercise.numbers.predecessorExercise
import org.simonolander.lambda.content.exercise.numbers.subtractionExercise
import org.simonolander.lambda.content.exercise.numbers.successorExercise
import org.simonolander.lambda.content.exercise.numbers.zeroExercise
import org.simonolander.lambda.content.exercise.pairs.firstExercise
import org.simonolander.lambda.content.exercise.pairs.secondExercise
import org.simonolander.lambda.domain.LevelId
import org.simonolander.lambda.domain.LevelViewComposable
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
    FUNCTIONS_AND_APPLICATIONS(
        id = LevelId("functions"),
        title = "Functions and applications",
        view = { SimpleDialogLevelView(functionsAndApplicationsDialog, it) },
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
    CURRYING(
        id = LevelId("currying"),
        title = "Currying",
        view = { SimpleDialogLevelView(curryingDialog, it) },
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
    APPLICATION_SYNTAX(
        id = LevelId("application-syntax"),
        title = "Application Syntax",
        view = { SimpleDialogLevelView(applicationSyntaxDialog, it) },
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
    FUNCTION_SYNTAX(
        id = LevelId("function-syntax"),
        title = "Function Syntax",
        view = { SimpleDialogLevelView(functionSyntaxDialog, it) },
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
        view = { LevelView(ifThenElseExercise, it) },
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
    SUCCESSOR(
        id = LevelId("successor"),
        title = "Successor",
        view = { LevelView(successorExercise, it) },
    ),
    ADDITION(
        id = LevelId("addition"),
        title = "Addition",
        view = { LevelView(additionExercise, it) },
    ),
    MULTIPLICATION(
        id = LevelId("multiplication"),
        title = "Multiplication",
        view = { LevelView(multiplicationExercise, it) },
    ),
    EXPONENTIATION(
        id = LevelId("exponentiation"),
        title = "Exponentiation",
        view = { LevelView(exponentiationExercise, it) },
    ),
    IS_ZERO(
        id = LevelId("is-zero"),
        title = "Zero",
        view = { LevelView(zeroExercise, it) },
    ),
    IS_EVEN(
        id = LevelId("is-even"),
        title = "Even",
        view = { LevelView(evenExercise, it) },
    ),
    PREDECESSOR(
        id = LevelId("predecessor"),
        title = "Predecessor",
        view = { LevelView(predecessorExercise, it) },
    ),
    SUBTRACTION(
        id = LevelId("subtraction"),
        title = "Subtraction",
        view = { LevelView(subtractionExercise, it) },
    ),
    LESS_THAN_OR_EQUAL(
        id = LevelId("less-than-or-equal"),
        title = "Less than or equal",
        view = { LevelView(lessThanOrEqualExercise, it) },
    ),
    EQUALS(
        id = LevelId("equals"),
        title = "Equals",
        view = { LevelView(equalsExercise, it) },
    ),
    PAIRS(
        id = LevelId("pairs"),
        title = "Defining the pair",
        view = { SimpleDialogLevelView(pairsDialog, it) },
    ),
    FIRST(
        id = LevelId("fst"),
        title = "First",
        view = { LevelView(firstExercise, it) },
    ),
    SECOND(
        id = LevelId("snd"),
        title = "Second",
        view = { LevelView(secondExercise, it) },
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
