package org.simonolander.lambda.content.dialog.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.misc.javascript
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.ui.theme.LambdaTheme

val applicationSyntaxDialog = run {
    DialogBuilder()
        .message("Hi! In this lesson, we're going to go through some more $lambdaCalculus syntax.")
        .message("First, let's take a look at parentheses.")
        .message("Parentheses are used to control the order of application.")
        .message("In a previous lesson, we looked at the expression f x y.") {
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
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Application order",
                style = MaterialTheme.typography.headlineMedium,
            )
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = lambdaCalculus,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = javascript,
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier.weight(1f),
                )
            }
            listOf(
                "f x y" to "f(x)(y)",
                "(f x) y" to "(f(x))(y)",
                "f (x y)" to "f(x(y))",
            ).forEach { (lc, js) ->
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = lc,
                        style = TextStyle(fontFamily = FontFamily.Monospace),
                        modifier = Modifier.weight(1f),
                    )
                    Text(
                        text = js,
                        style = TextStyle(fontFamily = FontFamily.Monospace),
                        modifier = Modifier.weight(1f),
                    )
                }
            }
        }
    }
}

@Composable
private fun JavascriptFunctionApplicationMaximalParentheses() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Parentheses",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = lambdaCalculus,
                style = MaterialTheme.typography.headlineSmall,
            )
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = "No redundant parentheses",
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = "Clarifying parentheses",
                    style = MaterialTheme.typography.bodySmall,
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
private fun JavascriptFunctionApplicationParenthesesPreview() {
    Surface {
        LambdaTheme {
            JavascriptFunctionApplicationParentheses()
        }
    }
}
@Preview
@Composable
private fun JavascriptFunctionApplicationMaximalParenthesesPreview() {
    Surface {
        LambdaTheme {
            JavascriptFunctionApplicationMaximalParentheses()
        }
    }
}
