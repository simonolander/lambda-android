package org.simonolander.lambda.content.dialog.intro

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
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

val curryingDialog = run {
    DialogBuilder()
        .message("Hello! So far, we've only worked with functions with a single parameter.")
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
        .message("As a side note, plus doesn't exist as a symbol in this way in $lambdaCalculus. We'll revisit that in a future chapter.")
        .message("Take a moment to wrap your head around currying, and we'll do some exercises with multiple parameters next!")
        .build()
}

@Composable
private fun JavascriptMultipleParameters() {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Multiple parameters",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = javascript,
                style = MaterialTheme.typography.headlineSmall,
            )
            CodeBlock(
                """
                    // 2 parameters
                    (x, y) => x + y
                """.trimIndent()
            )
            Spacer(modifier = Modifier.height(8.dp))
            CodeBlock(
                """
                    // 0 parameters
                    () => console.log('Hi')
                """.trimIndent()
            )
        }
    }
}

@Composable
private fun JavascriptCurrying() {
    Box(Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Currying",
                style = MaterialTheme.typography.headlineMedium,
            )
            Text(
                text = javascript,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = "A curried function",
                style = MaterialTheme.typography.bodySmall,
            )
            CodeBlock("x => y => x + y")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Application of curried function f to two arguments",
                style = MaterialTheme.typography.bodySmall,
            )
            CodeBlock("f(x)(y)")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = lambdaCalculus,
                style = MaterialTheme.typography.headlineSmall,
            )
            Text(
                text = "A curried function",
                style = MaterialTheme.typography.bodySmall,
            )
            CodeBlock("λ x. (λ y. (x + y))")
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Application of curried function f to two arguments",
                style = MaterialTheme.typography.bodySmall,
            )
            CodeBlock("f x y")
        }
    }
}

@Preview
@Composable
private fun JavascriptMultipleParametersPreview() {
    Surface {
        LambdaTheme {
            JavascriptMultipleParameters()
        }
    }
}

@Preview
@Composable
private fun JavascriptCurryingPreview() {
    Surface {
        LambdaTheme {
            JavascriptCurrying()
        }
    }
}
