package org.simonolander.lambda.content.dialog.intro

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.ui.theme.LambdaTheme

val functionSyntaxDialog = run {
    DialogBuilder()
        .message("Hi! Last time we talked about application syntax and associativity.")
        .message("This time we're going to talk about functions and parentheses.")
        .message("Functions always consume as much as possible to the right.") {
            FunctionParentheses()
        }
        .message("Here are a few examples of how functions work, with clarifying parentheses.")
        .message("I've removed some redundant spaces as well, to make everything fit.")
        .message("In general, the only time you need spaces is to separate variables.")
        .message("For example, λx.y is equal to λ x. y, but xy is not the same as x y.")
        .message("Additionally, writing functions with many parameters can be tedious with all the λ's.")
        .message("Instead of writing λx.λy.λz.x you can write λx y z. x, if you prefer. It means the same thing.")
        .build()
}

@Composable
private fun FunctionParentheses() {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Text(
                text = "Function parentheses",
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
                "λx.x y" to "λx.(x y)",
                "(λx.x) y" to "(λx.x) y",
                "λx.x y z" to "λx.((x y) z)",
                "λx.λy.y" to "λx.(λy.y)",
                "λx.x λy.y" to "λx.(x (λy.y))",
                "(λx.x) λy.y" to "(λx.x) (λy.y)",
                "λx.x λy.y z" to "λx.(x (λy.(y z)))",
                "λx.x (λy.y) z" to "λx.((x (λy.y)) z)",
                "(λx.x) (λy.y) z" to "((λx.x) (λy.y)) z",
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
private fun FunctionParenthesesPreview() {
    Surface {
        LambdaTheme {
            FunctionParentheses()
        }
    }
}
