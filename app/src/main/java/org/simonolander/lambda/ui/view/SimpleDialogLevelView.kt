package org.simonolander.lambda.ui.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.domain.Dialog
import org.simonolander.lambda.domain.Message
import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.ui.theme.LambdaTheme

/**
 * This composable renders a simple level consisting of a single dialog.
 * It's primarily used in the introduction.
 */
@Composable
fun SimpleDialogLevelView(
    initialDialog: Dialog?,
    onLevelCompleted: (Expression?) -> Unit,
    onNavigateToNextLevel: () -> Unit,
) {
    val (dialog, setDialog) = remember(initialDialog) {
        mutableStateOf(initialDialog)
    }

    val (view, setView) = remember(initialDialog) {
        mutableStateOf((initialDialog as? Message)?.view)
    }

    if (dialog != null) {
        val scrollState = rememberScrollState()
        Column(
            modifier = Modifier
                .fillMaxSize()
                .scrollableNoFling(scrollState)
                .verticalScroll(scrollState)
        ) {
            Box(Modifier.weight(1f)) {
                view?.invoke()
            }
            Box {
                DialogView(
                    dialog = dialog,
                    animationSpeed = 30f,
                    onNextDialog = { nextDialog ->
                        setDialog(nextDialog)
                        setView((nextDialog as? Message)?.view ?: view)
                        if (nextDialog == null) {
                            onLevelCompleted(null)
                        }
                    }
                )
            }
        }
    }
    else {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Lesson complete!",
                    style = MaterialTheme.typography.h4,
                )
                Text(
                    text = "🎉",
                    style = MaterialTheme.typography.h2,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onNavigateToNextLevel() }) {
                    Text(text = "Next level")
                }
            }
        }
    }
}


@Preview
@Composable
private fun SimpleDialogLevelViewPreview() {
    Surface {
        LambdaTheme {
            SimpleDialogLevelView(null, {}, {})
        }
    }
}
