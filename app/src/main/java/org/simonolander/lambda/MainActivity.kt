package org.simonolander.lambda

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import org.simonolander.lambda.ui.theme.LambdaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LambdaTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    Chapter1Level1()
                }
            }
        }
    }
}

@Preview(name = "light mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Preview(name = "dark mode", showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DefaultPreview() {
    Chapter1Level2()
}

@Composable
fun Chapter1Level1() {
    val context = LocalContext.current
    LambdaTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        Toast
                            .makeText(context, "hallå", Toast.LENGTH_SHORT)
                            .show()
                    }
            ) {
                Text(
                    text = "λx. x",
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center,
                )
                Text(
                    text = "This is a function",
                    style = MaterialTheme.typography.subtitle1,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}

@Composable
fun Chapter1Level2() {
    val context = LocalContext.current
    LambdaTheme {
        Surface {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .clickable {
                        Toast
                            .makeText(context, "hallå", Toast.LENGTH_SHORT)
                            .show()
                    }
            ) {
                val functionPieces = listOf(
                    "λ" to "λ starts the function" to colorResource(R.color.indigo_700),
                    "x" to "Parameters" to colorResource(R.color.red_800),
                    ". " to "End of parameters" to colorResource(R.color.teal_500),
                    "x" to "Function body" to colorResource(R.color.yellow_900),
                )
                Row {
                    functionPieces.forEach {
                        Text(
                            text = it.first.first,
                            color = it.second,
                            style = MaterialTheme.typography.h2,
                        )
                    }
                }
                functionPieces.forEach {
                    Text(
                        text = it.first.second,
                        color = it.second,
                        style = MaterialTheme.typography.subtitle1,
                    )
                }
            }
        }
    }
}
