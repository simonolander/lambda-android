package org.simonolander.lambda.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.data.andExercise
import org.simonolander.lambda.data.identity
import org.simonolander.lambda.engine.AND
import org.simonolander.lambda.engine.TRUE
import org.simonolander.lambda.engine.parse
import org.simonolander.lambda.ui.theme.LambdaTheme

@Composable
fun ExecutionView(
    viewModel: ExecutionViewModel,
    onSuccess: () -> Unit,
) {
    val exercise = viewModel.exercise
    val testCases = viewModel.executingTestCases
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = exercise.name,
            style = MaterialTheme.typography.h2,
        )

        Text(
            text = exercise.description,
            style = MaterialTheme.typography.subtitle1,
        )

        Spacer(Modifier.height(10.dp))

        Column(Modifier.fillMaxWidth()) {
            testCases.forEach { testCase ->
                TestCaseView(testCase)
            }
        }

        Spacer(Modifier.height(10.dp))

        ControlView(
            state = viewModel.state,
            testCases = testCases,
            onRun = viewModel::play,
            onPause = viewModel::pause,
            onStep = viewModel::step,
            onSuccess = onSuccess
        )
    }
}

@Composable
private fun TestCaseView(testCase: ExecutingTestCase) {
    Row(
        Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        when (testCase.state) {
            ExecutingTestCase.State.FAILED -> Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Failed",
                tint = MaterialTheme.colors.error,
                modifier = Modifier.fillMaxWidth(0.1f)
            )
            ExecutingTestCase.State.PENDING -> Icon(
                imageVector = Icons.Default.Schedule,
                contentDescription = "Pending",
                modifier = Modifier.fillMaxWidth(0.1f)
            )
            ExecutingTestCase.State.RUNNING -> Icon(
                imageVector = Icons.Default.PlayArrow,
                contentDescription = "Running",
                modifier = Modifier.fillMaxWidth(0.1f)
            )
            ExecutingTestCase.State.SUCCEEDED -> Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Succeeded",
                modifier = Modifier.fillMaxWidth(0.1f)
            )
        }

        Text(
            text = testCase.testCase.input.prettyPrint(),
            Modifier.fillMaxWidth(0.3f)
        )

        Text(
            text = testCase.testCase.output.prettyPrint(),
            Modifier.fillMaxWidth(0.3f)
        )

        if (testCase.state != ExecutingTestCase.State.PENDING) {
            val actual = testCase.reductions.lastOrNull()?.after ?: testCase.testCase.input
            Text(
                text = actual.prettyPrint(),
                Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun ControlView(
    state: ExecutionViewModel.State,
    testCases: List<ExecutingTestCase>,
    onRun: () -> Unit,
    onPause: () -> Unit,
    onStep: () -> Unit,
    onSuccess: () -> Unit,
) {
    when (state) {
        ExecutionViewModel.State.PAUSED ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(onClick = onStep) {
                    Text(text = "Step")
                }
                Button(onClick = onRun) {
                    Text(text = "Run")
                }
            }
        ExecutionViewModel.State.RUNNING -> {
            Button(onClick = onPause) {
                Text(text = "Pause")
            }
        }
        ExecutionViewModel.State.TERMINATED -> {
            val successful = testCases.all { it.state == ExecutingTestCase.State.SUCCEEDED }
            if (successful) {
                Button(onClick = onSuccess) {
                    Text("Next level")
                }
            }
            else {
                Button(onClick = { /*TODO*/ }) {
                    Text("Back")
                }
            }
        }
    }
}

@Preview
@Composable
private fun ExecutionScreenPreview() {
    val context = LocalContext.current
    val viewModel = ExecutionViewModel(
        andExercise,
        solution = parse("\\a b. true")
    )
    repeat(14) { viewModel.step() }
    LambdaTheme {
        Surface {
            ExecutionView(viewModel, onSuccess = {
                Toast.makeText(context, "onSuccess called", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
