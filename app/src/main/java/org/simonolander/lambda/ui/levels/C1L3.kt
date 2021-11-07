package org.simonolander.lambda.ui.levels

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.ui.theme.LambdaTheme

/**
 * This level aims to teach the user the syntax and mechanics of function application
 */
@Composable
fun C1L3(onLevelComplete: () -> Unit) {
    var page by remember {
        mutableStateOf(Pages.Initial)
    }
    val colorAnimationSpec = spring<Color>(stiffness = Spring.StiffnessVeryLow)
    val parenthesisColor by animateColorAsState(
        when (page) {
            Pages.Initial -> MaterialTheme.colors.onBackground
            Pages.Replace -> MaterialTheme.colors.onBackground.copy(alpha = 0f)
            Pages.Result -> MaterialTheme.colors.onBackground.copy(alpha = 0f)
        }, colorAnimationSpec
    )
    val lambdaColor by animateColorAsState(
        when (page) {
            Pages.Initial -> MaterialTheme.colors.primary
            Pages.Replace -> MaterialTheme.colors.onBackground
            Pages.Result -> MaterialTheme.colors.onBackground.copy(alpha = 0f)
        }, colorAnimationSpec
    )
    val parameterColor by animateColorAsState(
        when (page) {
            Pages.Initial -> MaterialTheme.colors.primary
            Pages.Replace -> MaterialTheme.colors.secondary
            Pages.Result -> MaterialTheme.colors.secondary.copy(alpha = 0f)
        }, colorAnimationSpec
    )
    val dotColor by animateColorAsState(
        when (page) {
            Pages.Initial -> MaterialTheme.colors.primary
            Pages.Replace -> MaterialTheme.colors.onBackground
            Pages.Result -> MaterialTheme.colors.onBackground.copy(alpha = 0f)
        }, colorAnimationSpec
    )
    val bodyColor by animateColorAsState(
        when (page) {
            Pages.Initial -> MaterialTheme.colors.primary
            Pages.Replace -> MaterialTheme.colors.secondary
            Pages.Result -> MaterialTheme.colors.primary
        }, colorAnimationSpec
    )
    val argumentColor by animateColorAsState(
        when (page) {
            Pages.Initial -> MaterialTheme.colors.secondary
            Pages.Replace -> MaterialTheme.colors.secondary.copy(alpha = 0f)
            Pages.Result -> MaterialTheme.colors.secondary.copy(alpha = 0f)
        }, colorAnimationSpec
    )
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clickable {
                if (page == Pages.Result) {
                    onLevelComplete()
                }
                page = when (page) {
                    Pages.Initial -> Pages.Replace
                    Pages.Replace -> Pages.Result
                    Pages.Result -> Pages.Result
                }
            }
    ) {
        Text(
            text = buildAnnotatedString {
                withStyle(SpanStyle(parenthesisColor)) {
                    append("(")
                }
                withStyle(SpanStyle(lambdaColor)) {
                    append("λ")
                }
                withStyle(SpanStyle(parameterColor, fontFamily = FontFamily.Monospace)) {
                    append(
                        when (page) {
                            Pages.Initial -> "x"
                            Pages.Replace -> "y"
                            Pages.Result -> "y"
                        }
                    )
                }
                withStyle(SpanStyle(dotColor)) {
                    append(". ")
                }
                withStyle(SpanStyle(bodyColor, fontFamily = FontFamily.Monospace)) {
                    append(
                        when (page) {
                            Pages.Initial -> "x"
                            Pages.Replace -> "y"
                            Pages.Result -> "y"
                        }
                    )
                }
                withStyle(SpanStyle(parenthesisColor)) {
                    append(") ")
                }
                withStyle(SpanStyle(argumentColor, fontFamily = FontFamily.Monospace)) {
                    append("y")
                }
            },
            style = MaterialTheme.typography.h2,
            modifier = Modifier.padding(24.dp)
        )
        Text(
            text = buildAnnotatedString {
                when (page) {
                    Pages.Initial -> {
                        append("This is a function application. The argument ")
                        withStyle(SpanStyle(MaterialTheme.colors.secondary)) {
                            append("y")
                        }
                        append(" is applied to the function ")
                        withStyle(SpanStyle(MaterialTheme.colors.primary)) {
                            append("λx. x")
                        }
                        append(". ")
                        append("The parentheses are necessary to keep ")
                        withStyle(SpanStyle(MaterialTheme.colors.secondary)) {
                            append("y")
                        }
                        append(" separate from the function.")
                    }
                    Pages.Replace -> {
                        append("To apply the argument to the function, we replace all the occurrences of the parameter with the argument.")
                    }
                    Pages.Result -> {
                        append("Lastly we simply remove the parameter, and what remains is the result of the application.")
                    }
                }
            },
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center
        )
    }
}

private enum class Pages {
    Initial,
    Replace,
    Result,
}
