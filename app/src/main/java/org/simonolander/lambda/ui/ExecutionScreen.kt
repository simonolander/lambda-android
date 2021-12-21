package org.simonolander.lambda.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.data.identity
import org.simonolander.lambda.engine.TRUE
import org.simonolander.lambda.ui.theme.LambdaTheme

@Composable
fun ExecutionScreen(
    viewModel: ExecutionViewModel,
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
            onPause = viewModel::pause,
            onRun = viewModel::play,
            onStep = viewModel::step,
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
                imageVector = Icons.Default.MoreVert,
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
    onRun: () -> Unit,
    onPause: () -> Unit,
    onStep: () -> Unit,
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
            // TODO what here?
        }
    }
}

@Preview
@Composable
private fun ExecutionScreenPreview() {
    val viewModel = ExecutionViewModel(
        identity,
        solution = TRUE.second
    )
    LambdaTheme {
        Surface {
            ExecutionScreen(viewModel)
        }
    }
}
