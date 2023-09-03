package org.simonolander.lambda.ui.view

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.domain.Dialog
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.domain.Message
import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.misc.javascript
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.ui.levels.CodeBlock
import org.simonolander.lambda.ui.theme.LambdaTheme

/**
 * This composable renders a simple level consisting of a single dialog.
 * It's primarily used in the introduction.
 */
@Composable
fun SimpleDialogLevelView(
    initialDialog: Dialog?,
    onLevelComplete: (Expression?) -> Unit,
) {
    val (dialog, setDialog) = remember(initialDialog) {
        mutableStateOf(initialDialog)
    }

    val (view, setView) = remember(initialDialog) {
        mutableStateOf((initialDialog as? Message)?.view)
    }

    if (dialog != null) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            val scrollState = rememberScrollState()
            Box(
                Modifier
                    .weight(1f)
                    .scrollableNoFling(scrollState)
                    .verticalScroll(scrollState)
            ) {
                view?.invoke()
            }
            Box {
                DialogView(dialog = dialog, animationSpeed = 30f, onNextDialog = {
                    setDialog(it)
                    setView((it as? Message)?.view ?: view)
                })
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Lesson complete!",
                    style = MaterialTheme.typography.headlineMedium,
                )
                Text(
                    text = "🎉",
                    style = MaterialTheme.typography.displayMedium,
                )
                Spacer(modifier = Modifier.height(16.dp))
                Button(onClick = { onLevelComplete(null) }) {
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
            SimpleDialogLevelView(initialDialog = null) {}
        }
    }
}

@Preview
@Composable
private fun LargeDialogLevelPreview() {
    Surface {
        LambdaTheme {
            SimpleDialogLevelView(
                initialDialog = DialogBuilder().message("""
                    In lambda calculus, we find,
                    functions of a unique design.
                    With abstraction so neat,
                    they're incredibly fleet,
                    solving problems of every kind!
                    """.trimIndent(), view = {
                    Box(
                        Modifier.fillMaxSize()
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp)
                        ) {
                            Text(
                                text = "Currying",
                                style = MaterialTheme.typography.headlineMedium,
                            )
                            Text(
                                text = javascript,
                                style = MaterialTheme.typography.headlineSmall,
                            )
                            Text(
                                text = "A curried function",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            CodeBlock("x => y => x + y")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Application of curried function f to two arguments",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            CodeBlock("f(x)(y)")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = lambdaCalculus,
                                style = MaterialTheme.typography.headlineSmall,
                            )
                            Text(
                                text = "A curried function",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            CodeBlock("λ x. (λ y. (x + y))")
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Application of curried function f to two arguments",
                                style = MaterialTheme.typography.bodySmall,
                            )
                            CodeBlock("f x y")
                        }
                    }
                }).build()
            ) {}
        }
    }
}
