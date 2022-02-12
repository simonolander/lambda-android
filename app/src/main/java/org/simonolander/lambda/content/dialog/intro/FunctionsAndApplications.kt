package org.simonolander.lambda.content.dialog.intro

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.domain.Character
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.misc.javascript
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.ui.levels.CodeBlock
import org.simonolander.lambda.ui.theme.LambdaTheme

val functionsAndApplicationsDialog = run {
    val knowsJavascript = DialogBuilder()
        .message("$javascript is perfect. As I said, $lambdaCalculus is all about functions.")
        .message("Here's a function in $javascript, and the same function written in $lambdaCalculus.") {
            JavascriptIdentityFunction()
        }
        .message("As you can see, the function takes an input x, and returns the same value x.")
        .message("The $lambdaCalculus function works the same way.")
        .message("""
            The λ marks the start of a function.
            
            The x before the dot is the name of the parameter.
            
            Everything after the dot is the body of the function.
        """.trimIndent())
        .message(
            "Here's an example of a function application. We take the same function we just looked at, and apply it to the argument y.",
        ) {
            ApplicationView()
        }
        .message("In both $javascript and $lambdaCalculus, the result is just y.")
        .message("Take a moment to understand the application, and we'll do some exercises next!")
        .build()

    val noneAbove = DialogBuilder()
        .message("Oh.")
        .message("Hi, developer here. I'll likely add additional languages for introduction here.",
            Character.Developer)
        .message("For now you'll have to make do with $javascript.", Character.Developer)
        .build(knowsJavascript) // TODO

    val knowsMathematics = DialogBuilder()
        .message("Hi, developer here. Great that you know mathematical functions.",
            Character.Developer)
        .message("Unfortunately, I haven't completed this part of the introduction.",
            Character.Developer)
        .message("For now, you'll have to make do with the $javascript introduction.",
            Character.Developer)
        .build(knowsJavascript) // TODO

    val doesNotKnowMathematics = DialogBuilder()
        .message("Hi, developer here. Unfortunately I haven't had time to complete this part of the introduction.",
            Character.Developer)
        .message("For now, you'll have to make do with the $javascript introduction.",
            Character.Developer)
        .build(knowsJavascript) // TODO

    val doesNotKnowProgramming = DialogBuilder()
        .message("That's alright.")
        .message("Have you seen functions like f(x)=x+1 in mathematics?")
        .question(
            "Are you familiar with functions in mathematics?",
            "Yes" to knowsMathematics,
            "No" to doesNotKnowMathematics,
        )

    val knowsProgramming = DialogBuilder()
        .message("That's great! It will make my job explaining functions easier.")
        .message("Which of the following programming languages do you know the best?")
        .question(
            "Which language do you know the best?",
            javascript to knowsJavascript,
            "None above" to noneAbove,
        )

    DialogBuilder()
        .message("Welcome to lesson 2! What is $lambdaCalculus about?")
        .message("At its core, $lambdaCalculus is a tiny symbol manipulation framework.")
        .message("It's used for describing computation. In fact, any computation can be described using $lambdaCalculus!")
        .message("But we're getting ahead of ourselves.")
        .message("$lambdaCalculus is all about functions.")
        .message("Before we dive deeper, I would like to know if you're familiar with some programming language.")
        .question(
            "Are you familiar with some programming language?",
            "Yes" to knowsProgramming,
            "No" to doesNotKnowProgramming,
        )
}

@Composable
private fun ApplicationView() {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Applying functions",
                style = MaterialTheme.typography.h4,
            )
            Text(
                text = javascript,
                style = MaterialTheme.typography.h5,
            )
            CodeBlock("(x => x)(y)")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = lambdaCalculus,
                style = MaterialTheme.typography.h5,
            )
            CodeBlock("(λ x. x) y")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Result",
                style = MaterialTheme.typography.h5,
            )
            CodeBlock("y")
        }
    }
}

@Composable
private fun JavascriptIdentityFunction() {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Example function",
                style = MaterialTheme.typography.h4,
            )
            Text(
                text = javascript,
                style = MaterialTheme.typography.h5,
            )
            CodeBlock("x => x")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = lambdaCalculus,
                style = MaterialTheme.typography.h5,
            )
            CodeBlock("λ x. x")
        }
    }
}

@Preview
@Composable
private fun FunctionsAndApplicationsPreview() {
    Surface {
        LambdaTheme {
            ApplicationView()
        }
    }
}
