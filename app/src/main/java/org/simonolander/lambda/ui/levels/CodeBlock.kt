package org.simonolander.lambda.ui.levels

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.theme.codeStyle

@Composable
fun CodeBlock(text: String) {
    Surface(
        color = Color.LightGray,
        shape = MaterialTheme.shapes.small,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Text(
                text = text,
                style = TextStyle.Default.plus(codeStyle),
            )
        }
    }
}

@Composable
@Preview
fun CodeBlockPreview() {
    androidx.compose.material.Surface {
        LambdaTheme {
            CodeBlock(text = """
                fun functionName(a, b) {
                    return a + b
                }
            """.trimIndent())
        }
    }
}
