package org.simonolander.lambda.content.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.misc.javascript
import org.simonolander.lambda.misc.lambda
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.ui.levels.CodeBlock
import org.simonolander.lambda.ui.theme.LambdaTheme

val whatIsLambdaCalculusDialog = run {
    val doesNotKnowProgramming = DialogBuilder()
        .message("That's alright.")
        .message("Have you seen functions like f(x)=x+1 in mathematics?")
        .question(
            "Are you familiar with functions in mathematics?",
            "Yes" to null,
            "No" to null,
        )

    val noneAbove = DialogBuilder()
        .message("Oh.")
        .message("We'll add more languages to the introduction, but that's going to be in the future.")
        .build(doesNotKnowProgramming)

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

    val knowsJavascript = DialogBuilder()
        .message("$javascript is perfect. As I said, $lambdaCalculus is all about functions.")
        .message("Here's a function in $javascript, and the same function written in $lambdaCalculus.") {
            JavascriptIdentityFunction()
        }
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
        .message("In $javascript, it's common to define functions with multiple parameters, or none at all!") {
            JavascriptMultipleParameters()
        }
        .message("In $lambdaCalculus, functions always have exactly one parameter.")
        .message("Functions with zero parameters simply don't exist, and functions with two or more parameters are created through currying.")
        .message("Here are examples of curried functions in $javascript and $lambdaCalculus.") {
            JavascriptCurrying()
        }
        .message("You can see that in both cases, the function f is applied first to the argument x, creating a new function.")
        .message("This new function is then applied to the argument y, which returns the final result.")
        .message("As a side note, plus doesn't exist as a symbol in this way in $lambdaCalculus. We'll revisit that in chapter 3.")
        .build()

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
        .message("It's used for describing computation. Fun fact: any computation can be described using $lambdaCalculus!")
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
private fun JavascriptIdentityFunction() {
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

@Composable
private fun JavascriptMultipleParameters() {
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

@Composable
private fun JavascriptCurrying() {
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
            Text(
                text = "A curried function",
                style = MaterialTheme.typography.caption,
            )
            CodeBlock("x => y => x + y")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Application of curried function f to two arguments",
                style = MaterialTheme.typography.caption,
            )
            CodeBlock("f(x)(y)")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = lambdaCalculus,
                style = MaterialTheme.typography.h6,
            )
            Text(
                text = "A curried function",
                style = MaterialTheme.typography.caption,
            )
            CodeBlock("λ x. (λ y. (x + y))")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Application of curried function f to two arguments",
                style = MaterialTheme.typography.caption,
            )
            CodeBlock("f x y")
        }
    }
}

@Preview
@Composable
private fun WhatIsLambdaCalculusPreview() {
    Surface {
        LambdaTheme {
            JavascriptCurrying()
        }
    }
}
