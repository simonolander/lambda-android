package org.simonolander.lambda.ui.levels

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.data.Exercise
import org.simonolander.lambda.data.andExercise
import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.ui.ExecutionState
import org.simonolander.lambda.ui.ExecutionView
import org.simonolander.lambda.ui.ExerciseView
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.view.DialogView

@Composable
fun LevelView(exercise: Exercise, onLevelComplete: () -> Unit) {
    val (answer, setAnswer) = remember {
        mutableStateOf<Expression?>(null)
    }

    val (dialog, setDialog) = remember {
        mutableStateOf(exercise.dialog)
    }

    if (answer == null) {
        Box {
            ExerciseView(
                exercise = exercise,
                onSubmit = setAnswer,
            )
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
            onSuccess = onLevelComplete,
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
