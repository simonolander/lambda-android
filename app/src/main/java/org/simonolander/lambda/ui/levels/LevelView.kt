package org.simonolander.lambda.ui.levels

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.simonolander.lambda.content.exercise.booleans.andExercise
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.ui.ExecutionState
import org.simonolander.lambda.ui.ExecutionView
import org.simonolander.lambda.ui.ExerciseDesignView
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.view.DialogView

@Composable
fun LevelView(exercise: Exercise, onLevelComplete: (Expression?) -> Unit) {
    val (answer, setAnswer) = remember {
        mutableStateOf<Expression?>(null)
    }

    val (dialog, setDialog) = remember {
        mutableStateOf(exercise.dialog)
    }

    if (answer == null) {
        Column(Modifier.fillMaxSize()) {
            Box(modifier = Modifier.weight(1f)) {
                ExerciseDesignView(
                    exercise = exercise,
                    onSubmit = setAnswer,
                )
            }
            if (dialog != null) {
                DialogView(
                    dialog = dialog,
                    onNextDialog = setDialog,
                )
            }
        }
    }
    else {
        val executionViewModel = ExecutionState(exercise, answer)
        ExecutionView(
            state = executionViewModel,
            onSuccess = { onLevelComplete(answer) },
        )
        BackHandler {
            setAnswer(null)
        }
    }
}

@Composable
@Preview
private fun LevelViewPreview() {
    val context = LocalContext.current
    Surface {
        LambdaTheme {
            LevelView(exercise = andExercise) {
                Toast.makeText(
                    context,
                    "Called onLevelComplete",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
