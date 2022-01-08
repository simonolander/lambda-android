package org.simonolander.lambda.ui.view

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SportsScore
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.simonolander.lambda.R
import org.simonolander.lambda.domain.Character
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
        mutableStateOf(if (animationSpeed != null) 0 else message.text.length)
    }

    val animating by derivedStateOf {
        numberOfCharactersToShow < message.text.length
    }

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
        }
        else {
            numberOfCharactersToShow = message.text.length
        }
    }

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 200.dp),
        onClick = {
            if (animating) {
                numberOfCharactersToShow = message.text.length
            }
            else {
                onNextDialog(message.next)
            }
        },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(width = 8.dp, color = Color.LightGray)
                .padding(8.dp)
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
                style = MaterialTheme.typography.h6,
                modifier = Modifier
                    .padding(8.dp)
                    .weight(4f)
            )
        }
        if (!animating) {
            Box {
                val imageVector =
                    if (message.next != null) {
                        Icons.Default.PlayArrow
                    }
                    else {
                        Icons.Default.SportsScore
                    }
                Icon(
                    imageVector = imageVector,
                    contentDescription = "Next",
                    modifier = Modifier
                        .size(64.dp)
                        .align(Alignment.BottomEnd)
                        .padding(8.dp),
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
            .border(width = 8.dp, color = Color.LightGray),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
        ) {
            Text(
                text = question.text,
                style = MaterialTheme.typography.body1,
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

@Composable
@Preview
private fun MessageViewPreview() {
    val message = Message(
        "In mathematics, Church encoding is a means of representing data and operators in the lambda calculus.",
        null
    )
    Surface {
        LambdaTheme {
            MessageView(message) {}
        }
    }
}

@Composable
@Preview
private fun QuestionViewPreview() {
    val question = Question(
        "How familiar are you with lambda calculus?",
        listOf(
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
