package org.simonolander.lambda.ui.screen

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.simonolander.lambda.content.Level
import org.simonolander.lambda.content.Level.*
import org.simonolander.lambda.content.dialog.intro.*
import org.simonolander.lambda.content.dialog.numbers.naturalNumbersDialog
import org.simonolander.lambda.content.dialog.pair.pairsDialog
import org.simonolander.lambda.content.exercise.basic.*
import org.simonolander.lambda.content.exercise.booleans.*
import org.simonolander.lambda.content.exercise.numbers.*
import org.simonolander.lambda.content.exercise.pairs.firstExercise
import org.simonolander.lambda.content.exercise.pairs.secondExercise
import org.simonolander.lambda.domain.Dialog
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.domain.LevelId
import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.engine.ParserException
import org.simonolander.lambda.ui.levels.LevelView
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.view.SimpleDialogLevelView

@Composable
fun LevelScreen(
    levelId: LevelId,
    onLevelCompleted: (Expression?) -> Unit,
    onNavigateToNextLevel: () -> Unit,
    onParseError: suspend () -> Boolean,
) {
    val level = Level.findById(levelId)
    if (level == null) {
        LevelNotFound(levelId)
    }
    else {
        @Composable
        fun DialogView(dialog: Dialog) {
            SimpleDialogLevelView(
                initialDialog = dialog,
                onLevelCompleted = onLevelCompleted,
                onNavigateToNextLevel = onNavigateToNextLevel,
            )
        }

        @Composable
        fun ExerciseView(exercise: Exercise) {
            LevelView(
                exercise = exercise,
                onLevelCompleted = onLevelCompleted,
                onNavigateToNextLevel = onNavigateToNextLevel,
                onParseError = onParseError,
            )
        }

        val unused = when (level) {
            HELLO -> DialogView(helloDialog)
            FUNCTIONS_AND_APPLICATIONS -> DialogView(functionsAndApplicationsDialog)
            IDENTITY -> ExerciseView(identityExercise)
            CONSTANT_FUNCTION -> ExerciseView(constantExercise)
            CURRYING -> DialogView(curryingDialog)
            KESTREL -> ExerciseView(kestrelExercise)
            KITE -> ExerciseView(kiteExercise)
            APPLICATION_SYNTAX -> DialogView(applicationSyntaxDialog)
            APPLICATOR -> ExerciseView(applicatorExercise)
            CARDINAL -> ExerciseView(cardinalExercise)
            FUNCTION_SYNTAX -> DialogView(functionSyntaxDialog)
            TRUE -> ExerciseView(trueExercise)
            FALSE -> ExerciseView(falseExercise)
            NOT -> ExerciseView(notExercise)
            AND -> ExerciseView(andExercise)
            OR -> ExerciseView(orExercise)
            IF_THEN_ELSE -> ExerciseView(ifThenElseExercise)
            EXCLUSIVE_OR -> ExerciseView(xorExercise)
            NATURAL_NUMBERS -> DialogView(naturalNumbersDialog)
            SUCCESSOR -> ExerciseView(successorExercise)
            ADDITION -> ExerciseView(additionExercise)
            MULTIPLICATION -> ExerciseView(multiplicationExercise)
            EXPONENTIATION -> ExerciseView(exponentiationExercise)
            IS_ZERO -> ExerciseView(zeroExercise)
            IS_EVEN -> ExerciseView(evenExercise)
            PREDECESSOR -> ExerciseView(predecessorExercise)
            SUBTRACTION -> ExerciseView(subtractionExercise)
            LESS_THAN_OR_EQUAL -> ExerciseView(lessThanOrEqualExercise)
            EQUALS -> ExerciseView(equalsExercise)
            PAIRS -> DialogView(pairsDialog)
            FIRST -> ExerciseView(firstExercise)
            SECOND -> ExerciseView(secondExercise)
        }
    }
}

@Composable
fun LevelNotFound(levelId: LevelId) {
    Text(
        text = "Level not found: ${levelId.value}",
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.error
    )
}

@Preview
@Composable
private fun LevelNotFoundPreview() {
    Surface {
        LambdaTheme {
            LevelNotFound(levelId = LevelId("does-not-exist"))
        }
    }
}
