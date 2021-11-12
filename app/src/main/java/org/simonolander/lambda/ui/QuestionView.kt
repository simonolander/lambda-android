package org.simonolander.lambda.ui

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.data.DialogEnd
import org.simonolander.lambda.data.Question
import org.simonolander.lambda.data.Response
import org.simonolander.lambda.ui.theme.LambdaTheme

@Composable
fun QuestionView(question: Question, onResponse: (Response) -> Unit) {
    val scrollState = rememberScrollState()
    Column {
        Text(
            text = question.text,
            modifier = Modifier.padding(bottom = 10.dp),
        )
        Column(
            modifier = Modifier
                .scrollable(scrollState, Orientation.Vertical)
                .verticalScroll(scrollState),
        ) {
            question.responses.forEach {
                ResponseView(it) { onResponse(it) }
            }
        }
    }
}

@Composable
private fun ResponseView(response: Response, onClick: () -> Unit) {
    Button(
        modifier = Modifier.padding(bottom = 10.dp),
        onClick = onClick
    ) {
        Text(text = response.first)
    }
}

@Preview
@Composable
private fun Preview() {
    LambdaTheme {
        Surface {
            QuestionView(
                Question(
                    "Have you had a good day?",
                    listOf(
                        "Yes, it's been quite chill." to DialogEnd,
                        "No, I haven't even had time to eat lunch." to DialogEnd,
                        "Meh." to DialogEnd,
                        "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English" to DialogEnd,
                    )
                ),
                onResponse = {}
            )
        }
    }
}
