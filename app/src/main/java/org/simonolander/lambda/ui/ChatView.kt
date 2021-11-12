package org.simonolander.lambda.ui

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import org.simonolander.lambda.data.ChatMessage
import org.simonolander.lambda.ui.theme.LambdaTheme

@Composable
fun ChatView(
    chatMessages: List<ChatMessage>,
    typing: Boolean,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
            .padding(10.dp)
            .scrollable(
                state = scrollState,
                orientation = Orientation.Vertical
            )
            .verticalScroll(scrollState)
            .clickable(onClick = onClick),
    ) {
        chatMessages.forEach {
            ChatMessageView(it)
        }
        if (typing) {
            LoadingView()
        }
    }
}

@Composable
fun LoadingView() {
    val activeColor = Color.Gray
    val inactiveColor = Color.LightGray
    val count by produceState(initialValue = 0) {
        while (true) {
            delay(250)
            value += 1
        }
    }
    val dotColor1 by animateColorAsState(
        if (count and 0b100 != 0) {
            activeColor
        } else {
            inactiveColor
        }
    )
    val dotColor2 by animateColorAsState(
        if (count and 0b010 != 0) {
            activeColor
        } else {
            inactiveColor
        }
    )
    val dotColor3 by animateColorAsState(
        if (count and 0b001 != 0) {
            activeColor
        } else {
            inactiveColor
        }
    )
    LambdaTheme {
        Surface {
            Row {
                Surface(
                    modifier = Modifier
                        .size(10.dp)
                        .padding(1.dp),
                    shape = CircleShape,
                    color = dotColor3,
                ) {}
                Surface(
                    modifier = Modifier
                        .size(10.dp)
                        .padding(1.dp),
                    shape = CircleShape,
                    color = dotColor2,
                ) {}
                Surface(
                    modifier = Modifier
                        .size(10.dp)
                        .padding(1.dp),
                    shape = CircleShape,
                    color = dotColor1,
                ) {}
            }
        }
    }
}

@Composable
private fun ColumnScope.ChatMessageView(chatMessage: ChatMessage) {
    val alignment = if (chatMessage.sent) {
        Modifier.align(Alignment.End)
    } else {
        Modifier.align(Alignment.Start)
    }
    val endPadding = 30.dp
    val padding = if (chatMessage.sent) {
        Modifier.padding(start = endPadding)
    } else {
        Modifier.padding(end = endPadding)
    }
    Surface(
        color = MaterialTheme.colors.primary,
        shape = RoundedCornerShape(10.dp),
        modifier = Modifier
            .padding(bottom = 4.dp)
            .then(alignment)
            .then(padding)
    ) {
        Text(
            text = chatMessage.text,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.padding(10.dp),
        )
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    LambdaTheme {
        Surface {
            ChatView(
                chatMessages = listOf(
                    ChatMessage("Hallå!"),
                    ChatMessage("Hej!", true),
                    ChatMessage(
                        "This mode is not recommended for production use, as no stability/compatibility guarantees are given on compiler or generated code. Use it at your own risk!",
                        true
                    ),
                    ChatMessage("This mode is not recommended for production use, as no stability/compatibility guarantees are given on compiler or generated code. Use it at your own risk!"),
                ),
                typing = true,
            ) {}
        }
    }
}
