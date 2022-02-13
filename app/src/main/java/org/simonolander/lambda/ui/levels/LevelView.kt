package org.simonolander.lambda.ui.levels

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import kotlinx.coroutines.launch
import org.simonolander.lambda.content.exercise.booleans.andExercise
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.EventType
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.misc.lambda
import org.simonolander.lambda.ui.ExecutionState
import org.simonolander.lambda.ui.ExecutionView
import org.simonolander.lambda.ui.ExerciseDesignView
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.view.DialogView

@Composable
fun LevelView(
    exercise: Exercise,
    onLevelCompleted: (Expression?) -> Unit,
    onNavigateToNextLevel: () -> Unit,
    onEvent: suspend (EventType) -> Boolean,
) {
    val (answer, setAnswer) = remember {
        mutableStateOf<Expression?>(null)
    }

    val (dialog, setDialog) = remember {
        mutableStateOf(exercise.dialog)
    }

    val coroutineScope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {
        Box(modifier = Modifier.weight(1f)) {
            if (answer == null) {
                ExerciseDesignView(
                    exercise = exercise,
                    onSubmit = { answer ->
                        setAnswer(answer)
                        coroutineScope.launch {
                            val firstEvent = onEvent(EventType.SUCCESSFUL_EXERCISE_SUBMISSION)
                            if (firstEvent) {
                                setDialog(
                                    DialogBuilder()
                                        .message("Alright, we have an answer!")
                                        .message("Let's run it through a couple of test cases to see that it matches the spec!")
                                        .message("Use the controls at the bottom to control the execution of the test cases. Good luck!")
                                        .build()
                                )
                            }
                        }
                    },
                    onParseError = {
                        coroutineScope.launch {
                            val firstEvent = onEvent(EventType.PARSE_ERROR)
                            if (firstEvent) {
                                setDialog(
                                    DialogBuilder()
                                        .message("Aj aj, it looks like you entered an invalid expression.")
                                        .message("Take a look at the error message, and try to correct the mistake.")
                                        .message("Quite often, it's something like a missing dot, $lambda, or parenthesis.")
                                        .build()
                                )
                            }
                        }
                    }
                )
            }
            else {
                val executionState = ExecutionState(
                    exercise = exercise,
                    solution = answer,
                    onSuccess = { onLevelCompleted(answer) }
                )
                ExecutionView(
                    state = executionState,
                    onFinish = onNavigateToNextLevel,
                )
                BackHandler {
                    setAnswer(null)
                }
            }
        }
        if (dialog != null) {
            DialogView(
                dialog = dialog,
                onNextDialog = setDialog,
                animationSpeed = 30f,
            )
        }
    }
}

@Composable
@Preview
private fun LevelViewPreview() {
    Surface {
        LambdaTheme {
            LevelView(
                exercise = andExercise,
                onLevelCompleted = {},
                onNavigateToNextLevel = {},
                onEvent = { false }
            )
        }
    }
}
