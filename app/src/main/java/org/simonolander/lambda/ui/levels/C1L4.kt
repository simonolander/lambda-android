package org.simonolander.lambda.ui.levels

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.google.android.material.color.MaterialColors
import org.simonolander.lambda.ui.theme.LambdaTheme

/**
 * This level aims to teach the user the syntactic sugar of the language
 */
@Composable
fun C1L4(onLevelComplete: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onLevelComplete() }
    ) {
        Text(
            text = "Syntactic sugar 🍬",
            style = MaterialTheme.typography.h2
        )
        val codeStyle = SpanStyle(
            color = Color.Red,
            fontFamily = FontFamily.Monospace,
            background = Color.LightGray
        )
        Text(
            text = buildAnnotatedString {
                append("The expression ")
                withStyle(codeStyle) {
                    append(" λa b c. a b c ")
                }
                append(" de-sugars to ")
                withStyle(codeStyle) {
                    append(" (λa. (λb. (λc. ((a b) c)))) ")
                }
                append(". Therefore the application ")
                withStyle(codeStyle) {
                    append(" (λa b c. a b c) x ")
                }
                append(" becomes ")
                withStyle(codeStyle) {
                    append(" λb c. x b c ")
                }
                append(".")
            },
            style = MaterialTheme.typography.body1
        )
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    LambdaTheme {
        Surface {
            C1L4 {

            }
        }
    }
}
