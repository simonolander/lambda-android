package org.simonolander.lambda.ui.levels

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import org.simonolander.lambda.data.Exercise
import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.ui.ExecutionView
import org.simonolander.lambda.ui.ExecutionViewModel
import org.simonolander.lambda.ui.ExerciseView

@Composable
fun LevelView(exercise: Exercise, onLevelComplete: () -> Unit) {
    val (answer, setAnswer) = remember {
        mutableStateOf<Expression?>(null)
    }

    if (answer == null) {
        ExerciseView(
            exercise = exercise,
            onSubmit = setAnswer,
        )
    }
    else {
        val executionViewModel = ExecutionViewModel(exercise, answer)
        ExecutionView(
            viewModel = executionViewModel,
            onSuccess = onLevelComplete,
        )
    }
}
