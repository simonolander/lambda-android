package org.simonolander.lambda.ui

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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
                style = MaterialTheme.typography.displayMedium,
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Your answer",
                style = MaterialTheme.typography.titleLarge,
            )

            Text(
                text = state.solution.prettyPrint(),
                style = MaterialTheme.typography.titleMedium,
            )

            Spacer(Modifier.height(10.dp))

            Text(
                text = "Test cases",
                style = MaterialTheme.typography.titleLarge,
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
                        style = MaterialTheme.typography.titleMedium,
                    )
                    when (val steps = testCase.reductions.count()) {
                        0 -> {} // Do not write anything
                        1 -> Text(
                            text = "$steps step",
                            style = MaterialTheme.typography.bodySmall,
                        )
                        else -> Text(
                            text = "$steps steps",
                            style = MaterialTheme.typography.bodySmall,
                        )
                    }
                }
                Text(
                    text = "Expected",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(text = testCase.testCase.output.prettyPrint())
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Current",
                    style = MaterialTheme.typography.bodySmall,
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
                    style = MaterialTheme.typography.titleMedium,
                )
                val numberOfReductions = testCase.reductions.count()
                Text(
                    text = "$numberOfReductions steps",
                    style = MaterialTheme.typography.bodySmall,
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
                        style = MaterialTheme.typography.titleMedium,
                    )
                    val numberOfReductions = testCase.reductions.count()
                    Text(
                        text = "$numberOfReductions steps",
                        style = MaterialTheme.typography.bodySmall,
                    )
                }
                Text(
                    text = "Expected",
                    style = MaterialTheme.typography.bodySmall,
                )
                Text(text = testCase.testCase.output.prettyPrint())
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Actual",
                    style = MaterialTheme.typography.bodySmall,
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
                style = MaterialTheme.typography.titleMedium,
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
                        FinishButton(onSuccess)
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
private fun FinishButton(onClick: () -> Unit) {
    ControlButton(
        imageVector = Icons.Default.SportsScore,
        contentDescription = "Finish lesson",
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
        containerColor = backgroundColor,
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
