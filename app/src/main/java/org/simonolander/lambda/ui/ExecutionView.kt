package org.simonolander.lambda.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.content.exercise.booleans.andExercise
import org.simonolander.lambda.engine.parse
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.view.scrollableNoFling

@Composable
fun ExecutionView(
    state: ExecutionState,
    onSuccess: () -> Unit,
) {
    val exercise = state.exercise
    val testCases = state.executingTestCases
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .scrollableNoFling(scrollState)
                .verticalScroll(scrollState)
                .weight(1f)
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
                text = state.solution.prettyPrint(),
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
        }

        ControlView(
            state = state.state,
            testCases = testCases,
            onRun = { state.run(coroutineScope) },
            onPause = state::pause,
            onStep = state::step,
            onSuccess = onSuccess,
            onReset = state::reset,
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
    val backgroundColor = if (isSystemInDarkTheme()) {
        Color(0xFF4A148C)
    }
    else {
        Color(0xFFCBD2F7)
    }
    Surface(
        color = backgroundColor
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
    val backgroundColor = if (isSystemInDarkTheme()) {
        Color(0xFF004D40)
    }
    else {
        Color(0xffd3f7cb)
    }
    Surface(
        color = backgroundColor
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
    val backgroundColor = if (isSystemInDarkTheme()) {
        Color(0xFFBF360C)
    }
    else {
        Color(0xfff7d0cb)
    }
    Surface(
        color = backgroundColor
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
    val backgroundColor = if (isSystemInDarkTheme()) {
        Color(0xFF6F6F6F)
    }
    else {
        Color(0xFFECECEC)
    }
    Surface(
        color = backgroundColor
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
    state: ExecutionState.State,
    testCases: List<ExecutingTestCase>,
    onRun: () -> Unit,
    onPause: () -> Unit,
    onStep: () -> Unit,
    onSuccess: () -> Unit,
    onReset: () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth()
    ) {
        val backgroundColor = if (isSystemInDarkTheme()) {
            Color(36, 36, 36, 255)
        }
        else {
            Color.LightGray
        }
        Row(
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .background(backgroundColor)
                .padding(8.dp)
        ) {
            when (state) {
                ExecutionState.State.PAUSED -> {
                    ResetButton(onReset)
                    RunButton(onRun)
                    StepButton(onStep)
                }
                ExecutionState.State.RUNNING -> {
                    PauseButton(onPause)
                }
                ExecutionState.State.TERMINATED -> {
                    ResetButton(onReset)
                    val successful =
                        testCases.all { it.state == ExecutingTestCase.State.SUCCESSFUL }
                    if (successful) {
                        Button(onClick = onSuccess) {
                            Text("Next level")
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun ResetButton(onClick: () -> Unit) {
    ControlButton(
        imageVector = Icons.Default.Replay,
        contentDescription = "Reset",
        onClick = onClick,
    )
}

@Composable
private fun PauseButton(onClick: () -> Unit) {
    ControlButton(
        imageVector = Icons.Default.Pause,
        contentDescription = "Pause",
        onClick = onClick,
    )
}

@Composable
private fun RunButton(onClick: () -> Unit) {
    ControlButton(
        imageVector = Icons.Default.PlayArrow,
        contentDescription = "Run",
        onClick = onClick,
    )
}

@Composable
private fun StepButton(onClick: () -> Unit) {
    ControlButton(
        imageVector = Icons.Default.SkipNext,
        contentDescription = "Step",
        onClick = onClick,
    )
}

@Composable
private fun ControlButton(
    imageVector: ImageVector,
    contentDescription: String,
    onClick: () -> Unit,
) {
    val (backgroundColor, contentColor) = if (isSystemInDarkTheme()) {
        Color.DarkGray to Color.White
    }
    else {
        Color(0xFF555555) to Color.White
    }
    FloatingActionButton(
        onClick = onClick,
        elevation = FloatingActionButtonDefaults.elevation(0.dp),
        backgroundColor = backgroundColor,
        contentColor = contentColor,
    ) {
        Icon(
            imageVector = imageVector,
            contentDescription = contentDescription,
        )
    }
}

@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
private fun ExecutionScreenPreview() {
    val context = LocalContext.current
    val viewModel = ExecutionState(
        andExercise,
        solution = parse("\\a b. true")
    )
    repeat(13) { viewModel.step() }
    LambdaTheme {
        Surface {
            ExecutionView(viewModel, onSuccess = {
                Toast.makeText(context, "onSuccess called", Toast.LENGTH_SHORT).show()
            })
        }
    }
}
