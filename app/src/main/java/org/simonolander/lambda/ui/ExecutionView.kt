package org.simonolander.lambda.ui

import android.widget.Toast
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.data.andExercise
import org.simonolander.lambda.engine.parse
import org.simonolander.lambda.ui.theme.LambdaTheme

@Composable
fun ExecutionView(
    viewModel: ExecutionViewModel,
    onSuccess: () -> Unit,
) {
    val exercise = viewModel.exercise
    val testCases = viewModel.executingTestCases
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
            .scrollable(scrollState, Orientation.Vertical)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = exercise.name,
            style = MaterialTheme.typography.h2,
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Your answer",
            style = MaterialTheme.typography.h6,
        )

        Text(
            text = viewModel.solution.prettyPrint(),
            style = MaterialTheme.typography.subtitle1,
        )

        Spacer(Modifier.height(10.dp))

        Text(
            text = "Test cases",
            style = MaterialTheme.typography.h6,
        )

        Spacer(Modifier.height(10.dp))

        Column(Modifier.fillMaxWidth()) {
            testCases.forEach { testCase ->
                TestCaseView(testCase)
                Spacer(modifier = Modifier.height(5.dp))
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
    when (testCase.state) {
        ExecutingTestCase.State.RUNNING -> TestCaseRunningView(testCase)
        ExecutingTestCase.State.SUCCESSFUL -> TestCaseSuccessfulView(testCase)
        ExecutingTestCase.State.FAILED -> TestCaseFailedView(testCase)
        ExecutingTestCase.State.PENDING -> TestCasePendingView(testCase)
    }
}

@Composable
fun TestCaseRunningView(testCase: ExecutingTestCase) {
    Surface(
        color = Color(0xFFCBD2F7)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
        ) {
            Column {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = "Running",
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = testCase.testCase.input.prettyPrint(),
                        style = MaterialTheme.typography.subtitle1,
                    )
                    when (val steps = testCase.reductions.count()) {
                        0 -> {} // Do not write anything
                        1 -> Text(
                            text = "$steps step",
                            style = MaterialTheme.typography.caption,
                        )
                        else -> Text(
                            text = "$steps steps",
                            style = MaterialTheme.typography.caption,
                        )
                    }
                }
                Text(
                    text = "Expected",
                    style = MaterialTheme.typography.caption,
                )
                Text(text = testCase.testCase.output.prettyPrint())
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Current",
                    style = MaterialTheme.typography.caption,
                )
                val actual = testCase.reductions.lastOrNull()?.after
                    ?: testCase.testCase.input
                Text(text = actual.prettyPrint())
            }
        }
    }
}

@Composable
fun TestCaseSuccessfulView(testCase: ExecutingTestCase) {
    Surface(
        color = Color(0xffd3f7cb)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Check,
                contentDescription = "Successful",
            )
            Spacer(modifier = Modifier.width(5.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = testCase.testCase.input.prettyPrint(),
                    style = MaterialTheme.typography.subtitle1,
                )
                val numberOfReductions = testCase.reductions.count()
                Text(
                    text = "$numberOfReductions steps",
                    style = MaterialTheme.typography.caption,
                )
            }
        }
    }
}

@Composable
fun TestCaseFailedView(testCase: ExecutingTestCase) {
    Surface(
        color = Color(0xfff7d0cb)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
        ) {
            Column {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Failed",
                )
            }
            Spacer(modifier = Modifier.width(5.dp))
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Text(
                        text = testCase.testCase.input.prettyPrint(),
                        style = MaterialTheme.typography.subtitle1,
                    )
                    val numberOfReductions = testCase.reductions.count()
                    Text(
                        text = "$numberOfReductions steps",
                        style = MaterialTheme.typography.caption,
                    )
                }
                Text(
                    text = "Expected",
                    style = MaterialTheme.typography.caption,
                )
                Text(text = testCase.testCase.output.prettyPrint())
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Actual",
                    style = MaterialTheme.typography.caption,
                )
                val actual = testCase.reductions.lastOrNull()?.after
                    ?: testCase.testCase.input
                Text(text = actual.prettyPrint())
            }
        }
    }
}

@Composable
fun TestCasePendingView(testCase: ExecutingTestCase) {
    Surface(
        color = Color(0xFFECECEC)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Schedule,
                contentDescription = "Pending",
            )
            Spacer(modifier = Modifier.width(5.dp))
            Text(
                text = testCase.testCase.input.prettyPrint(),
                style = MaterialTheme.typography.subtitle1,
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
            val successful = testCases.all { it.state == ExecutingTestCase.State.SUCCESSFUL }
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
        solution = parse("\\a b. a b b")
    )
    repeat(19) { viewModel.step() }
    LambdaTheme {
        Surface {
            ExecutionView(viewModel, onSuccess = {
                Toast.makeText(context, "onSuccess called", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
