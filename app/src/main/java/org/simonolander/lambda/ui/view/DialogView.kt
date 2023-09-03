package org.simonolander.lambda.ui.view

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.simonolander.lambda.domain.Dialog
import org.simonolander.lambda.domain.Message
import org.simonolander.lambda.domain.Question
import org.simonolander.lambda.ui.theme.LambdaTheme

@Composable
fun DialogView(dialog: Dialog, animationSpeed: Float? = null, onNextDialog: (Dialog?) -> Unit) {
    when (dialog) {
        is Message -> MessageView(
            message = dialog,
            animationSpeed = animationSpeed,
            onNextDialog = onNextDialog,
        )

        is Question -> QuestionView(dialog, onNextDialog)
    }
}

@Composable
private fun MessageView(
    message: Message,
    animationSpeed: Float? = null, // Characters per second
    onNextDialog: (Dialog?) -> Unit,
) {
    var numberOfCharactersToShow by remember(message, animationSpeed) {
        mutableIntStateOf(if (animationSpeed != null) 0 else message.text.length)
    }

    val animating = numberOfCharactersToShow < message.text.length

    LaunchedEffect(message, animationSpeed) {
        if (animationSpeed != null) {
            numberOfCharactersToShow = 0
            val characterDelay = 1000f / animationSpeed
            if (characterDelay.isFinite() && characterDelay > 0) {
                while (numberOfCharactersToShow < message.text.length) {
                    numberOfCharactersToShow += 1
                    delay(characterDelay.toLong())
                }
            }
        } else {
            numberOfCharactersToShow = message.text.length
        }
    }

    Surface(
        onClick = {
            if (animating) {
                numberOfCharactersToShow = message.text.length
            } else {
                onNextDialog(message.next)
            }
        },
    ) {
        Box(
            Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 200.dp)
                .border(width = 8.dp, color = borderColor(isSystemInDarkTheme()))
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painterResource(message.speaker.profilePicture),
                    contentDescription = "Profile picture",
                    modifier = Modifier
                        .weight(1f, false)
                        .padding(8.dp)
                        .aspectRatio(1f)
                )
                Text(
                    text = message.text.take(numberOfCharactersToShow),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(8.dp)
                        .weight(4f)
                )
            }
            if (!animating) {
                val imageVector = if (message.next != null) {
                    Icons.Default.PlayArrow
                } else {
                    Icons.Default.SportsScore
                }
                Icon(
                    imageVector = imageVector,
                    contentDescription = "Next",
                    modifier = Modifier
                        .size(48.dp)
                        .align(Alignment.BottomEnd),
                    tint = Color.LightGray,
                )
            }
        }
    }
}

@Composable
private fun QuestionView(question: Question, onNextDialog: (Dialog?) -> Unit) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp)
            .border(width = 8.dp, color = borderColor(isSystemInDarkTheme())),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = question.text,
                style = MaterialTheme.typography.bodyLarge,
            )
            Spacer(modifier = Modifier.height(8.dp))
            question.responses.forEachIndexed { index, (text, next) ->
                Button(onClick = { onNextDialog(next) }) {
                    Text(
                        text = text,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                if (index < question.responses.size - 1) {
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}

private fun borderColor(isSystemInDarkTheme: Boolean): Color {
    return if (isSystemInDarkTheme) Color.DarkGray
    else Color.LightGray
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
private fun MessageViewPreview() {
    val message = Message(
        """
        In mathematics, Church encoding is a means of representing data and operators in the lambda calculus.
        
        For example, Church numerals are a classic illustration of how natural numbers can be encoded using lambda expressions.
        """.trimIndent(), null
    )
    Surface {
        LambdaTheme {
            MessageView(message) {}
        }
    }
}

@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
private fun QuestionViewPreview() {
    val question = Question(
        "How familiar are you with lambda calculus?", listOf(
            "Not at all" to null,
            "Somewhat familiar" to null,
            "Quite familiar" to null,
        )
    )
    Surface {
        LambdaTheme {
            QuestionView(question) {}
        }
    }
}
