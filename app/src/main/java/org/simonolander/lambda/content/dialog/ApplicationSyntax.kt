package org.simonolander.lambda.content.dialog

import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.data.DialogBuilder
import org.simonolander.lambda.misc.javascript
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.ui.theme.LambdaTheme

val applicationSyntaxDialog = run {
    DialogBuilder()
        .message("Hi! In this lesson, we're going to go through some $lambdaCalculus syntax.")
        .message("First, let's take a look at parentheses.")
        .message("Parentheses are used to control the order of application.")
        .message("In the previous lesson, we looked at the expression f x y.") {
            JavascriptFunctionApplicationParentheses()
        }
        .message("Here are three examples of how parentheses change the order of application.")
        .message("The first example f x y have no parentheses.")
        .message("The second example (f x) y evaluate to the same result as the first example.")
        .message("That's because function application is left associative in $lambdaCalculus!")
        .message("I've added the same redundant parentheses to the $javascript example as well.")
        .message("In general, I will omit redundant parentheses like these.")
        .message("The third example f (x y) does however change the meaning of the expression!")
        .message("In the third example, we first apply x to y. Then we apply f to the result of the first application.")
        .message("Here are more examples using four symbols.") {
            JavascriptFunctionApplicationMaximalParentheses()
        }
        .message("As you can see, with four symbols there are five distinct applications that we can create.")
        .message("Take a moment to understand these before moving on to the next lesson.")
        .build()
}

@Composable
private fun JavascriptFunctionApplicationParentheses() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = lambdaCalculus,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = javascript,
                    style = MaterialTheme.typography.h6,
                    modifier = Modifier.weight(1f),
                )
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "f x y",
                    style = TextStyle(fontFamily = FontFamily.Monospace),
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "f(x)(y)",
                    style = TextStyle(fontFamily = FontFamily.Monospace),
                    modifier = Modifier.weight(1f),
                )
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "(f x) y",
                    style = TextStyle(fontFamily = FontFamily.Monospace),
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "(f(x))(y)",
                    style = TextStyle(fontFamily = FontFamily.Monospace),
                    modifier = Modifier.weight(1f),
                )
            }
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "f (x y)",
                    style = TextStyle(fontFamily = FontFamily.Monospace),
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "f(x(y))",
                    style = TextStyle(fontFamily = FontFamily.Monospace),
                    modifier = Modifier.weight(1f),
                )
            }
        }
    }
}

@Composable
private fun JavascriptFunctionApplicationMaximalParentheses() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = lambdaCalculus,
                style = MaterialTheme.typography.h5,
            )
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "No redundant parentheses",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "Clarifying parentheses",
                    style = MaterialTheme.typography.caption,
                    modifier = Modifier.weight(1f),
                )
            }
            listOf(
                "f x y z" to "((f x) y) z",
                "f (x y) z" to "(f (x y)) z",
                "f x (y z)" to "(f x) (y z)",
                "f (x y z)" to "f ((x y) z)",
                "f (x (y z))" to "f (x (y z))",
            ).forEach { (minimal, clarifying) ->
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = minimal,
                        style = TextStyle(fontFamily = FontFamily.Monospace),
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = clarifying,
                        style = TextStyle(fontFamily = FontFamily.Monospace),
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun ApplicationSyntaxDialogPreview() {
    Surface {
        LambdaTheme {
            JavascriptFunctionApplicationMaximalParentheses()
        }
    }
}
