package org.simonolander.lambda.ui.levels

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.simonolander.lambda.data.DialogBuilder
import org.simonolander.lambda.data.Message
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.view.DialogView

private val helloWorldDialog = run {
    val knowsMathematics = Message()
    val doesNotKnowMathematics = Message()
    val doesNotKnowProgramming = DialogBuilder()
        .message("That's alright.")
        .message("Have you seen functions like f(x)=x+1 in mathematics?")
        .question(
            "Are you familiar with functions in mathematics?",
            "Yes" to knowsMathematics,
            "No" to doesNotKnowMathematics,
        )

    val noneAbove = DialogBuilder()
        .message("Oh.")
        .build(doesNotKnowProgramming)

    val knowsProgramming = DialogBuilder()
        .message("That's great! That makes my job explaining functions so much easier.")
        .message("Which of the following programming languages do you know the best?")
        .question(
            "Which language do you know the best?",
            "Java" to Message(),
            "Javascript" to Message(),
            "Haskell" to Message(),
            "None above" to noneAbove,
        )
    DialogBuilder()
        .message("So what is lambda calculus, anyway?")
        .message("Well, it's a way of describing computations. Any computation.")
        .message("But we're getting ahead of ourselves.")
        .message("Lambda calculus is all about functions.")
        .message("By chance, are you familiar with some programming language?")
        .question(
            "Are you familiar with a programming language?",
            "Yes" to knowsProgramming,
            "No" to doesNotKnowProgramming,
        )
}

@Composable
fun WhatAreFunctionsView(onLevelComplete: () -> Unit) {
    val (dialog, setDialog) = remember {
        mutableStateOf(helloWorldDialog)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            DialogView(
                dialog = dialog,
                animationSpeed = 30f,
                onNextDialog = {
                    if (it != null) {
                        setDialog(it)
                    }
                    else {
                        onLevelComplete()
                    }
                }
            )
        }
    }
}

@Composable
@Preview
private fun WhatAreFunctionsPreview() {
    val context = LocalContext.current
    Surface {
        LambdaTheme {
            WhatAreFunctionsView {
                Toast.makeText(
                    context,
                    "Called onLevelComplete",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
