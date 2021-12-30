package org.simonolander.lambda.ui.levels

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.data.DialogBuilder
import org.simonolander.lambda.data.Message
import org.simonolander.lambda.misc.javascript
import org.simonolander.lambda.misc.lambda
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.view.DialogView

private val dialog = run {
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

    val identityFunctionView = @Composable {
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = javascript,
                    style = MaterialTheme.typography.h6,
                )
                CodeBlock("x => x")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = lambdaCalculus,
                    style = MaterialTheme.typography.h6,
                )
                CodeBlock("λ x. x")
            }
        }
    }

    val applicationView = @Composable {
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = javascript,
                    style = MaterialTheme.typography.h6,
                )
                CodeBlock("(x => x)(y)")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = lambdaCalculus,
                    style = MaterialTheme.typography.h6,
                )
                CodeBlock("(λ x. x) y")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Result",
                    style = MaterialTheme.typography.h6,
                )
                CodeBlock("y")
            }
        }
    }

    val jsMultipleParameters = @Composable {
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = javascript,
                    style = MaterialTheme.typography.h6,
                )
                CodeBlock("""
                    // 2 parameters
                    (x, y) => x + y
                """.trimIndent())
                Spacer(modifier = Modifier.height(8.dp))
                CodeBlock("""
                    // 0 parameters
                    () => console.log('Hi')
                """.trimIndent())
            }
        }
    }

    val currying = @Composable {
        Box(Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .align(Alignment.Center)
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = javascript,
                    style = MaterialTheme.typography.h6,
                )
                CodeBlock("x => y => x + y")
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = lambdaCalculus,
                    style = MaterialTheme.typography.h6,
                )
                CodeBlock("λ x. (λ y. (x + y))")
            }
        }
    }

    val knowsJavascript = DialogBuilder()
        .message("$javascript is perfect. As I said, $lambdaCalculus is all about functions.")
        .message("Here's a function in $javascript, and the same function written in $lambdaCalculus.",
            identityFunctionView)
        .message("As you can see, the function takes an input x, and returns the same value x.")
        .message("The $lambdaCalculus function works the same way.")
        .message("""
            The $lambda marks the start of a function.
            
            The x before the dot is the name of the parameter.
            
            Everything after the dot is the body of the function.
        """.trimIndent())
        .message(
            "Here's an example of a function application. We take the same function we just looked at, and apply it to the argument y.",
            applicationView,
        )
        .message("In both $javascript and $lambdaCalculus, the result is just y.")
        .message("In $javascript, it's common to define functions with multiple parameters, or none at all!",
            jsMultipleParameters)
        .build()

    val knowsProgramming = DialogBuilder()
        .message("That's great! It will make my job explaining functions so much easier.")
        .message("Which of the following programming languages do you know the best?")
        .question(
            "Which language do you know the best?",
            javascript to knowsJavascript,
            "None above" to noneAbove,
        )

    val preview = currying
    DialogBuilder()
        .message("So what is $lambdaCalculus, anyway?", preview)
        .message("Well, it's a way of describing computations. Any computation.")
        .message("But we're getting ahead of ourselves.")
        .message("$lambdaCalculus is all about functions.")
        .message("Before we dive into it, I would like to know if you're familiar with some programming language.")
        .question(
            "Are you familiar with some programming language?",
            "Yes" to knowsProgramming,
            "No" to doesNotKnowProgramming,
        )
}

@Composable
fun WhatAreFunctionsView(onLevelComplete: () -> Unit) {
    val (dialog, setDialog) = remember {
        mutableStateOf(dialog)
    }

    val (view, setView) = remember {
        mutableStateOf((dialog as? Message)?.view)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(Modifier.weight(1f)) {
            view?.invoke()
        }
        Box {
            DialogView(
                dialog = dialog,
                animationSpeed = 30f,
                onNextDialog = {
                    if (it != null) {
                        setDialog(it)
                        setView((it as? Message)?.view ?: view)
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
