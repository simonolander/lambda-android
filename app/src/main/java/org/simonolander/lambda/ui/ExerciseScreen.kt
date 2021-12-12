package org.simonolander.lambda.ui

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.data.Exercise
import org.simonolander.lambda.data.TestCase
import org.simonolander.lambda.engine.*
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.theme.codeStyle

@Composable
fun ExerciseScreen(exercise: Exercise, onSubmit: (Expression) -> Unit) {
    var solutionValue by remember {
        mutableStateOf(TextFieldValue())
    }
    var parserException: ParserException? by remember {
        mutableStateOf(null)
    }

    fun validateAndSubmit() {
        try {
            val expression = parse(solutionValue.text)
            onSubmit(expression)
        } catch (e: ParserException) {
            parserException = e
        }
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            text = exercise.name,
            style = MaterialTheme.typography.h2,
        )
        Text(
            text = exercise.description,
            style = MaterialTheme.typography.subtitle1,
        )
        Spacer(modifier = Modifier.height(10.dp))
        TextField(
            value = solutionValue,
            onValueChange = {
                solutionValue = it
                parserException = null
            },
            label = { Text(text = "id") },
            placeholder = { Text(text = "λa. b") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(
                autoCorrect = false,
                imeAction = ImeAction.Go,
            ),
            trailingIcon = {
                IconButton(onClick = {
                    solutionValue = TextFieldValue(
                        text = buildString {
                            append(solutionValue.text.take(solutionValue.selection.min))
                            append("λ")
                            append(solutionValue.text.drop(solutionValue.selection.max))
                        },
                        selection = TextRange(solutionValue.selection.min + 1)
                    )
                }) {
                    Icon(
                        imageVector = Icons.Default.Favorite,
                        contentDescription = "Insert λ"
                    )
                }
            },
            keyboardActions = KeyboardActions {
                validateAndSubmit()
            },
            isError = parserException != null
        )
        Spacer(modifier = Modifier.height(10.dp))
        with(parserException) {
            if (this != null) {
                Text(
                    text = message,
                    color = MaterialTheme.colors.error,
                )
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
        Button(
            onClick = { validateAndSubmit() },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Go")
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    val exercise = Exercise(
        name = "Identity",
        description = buildAnnotatedString {
            append("Design a function ")
            withStyle(codeStyle) { append("id") }
            append(", that given any single input ")
            withStyle(codeStyle) { append("x") }
            append(" produces the same output ")
            withStyle(codeStyle) { append("x") }
            append(". ")
            append("\n\n")
            append("For example, ")
            withStyle(codeStyle) { append("id a") }
            append(" should reduce to ")
            withStyle(codeStyle) { append("a") }
            append(".")
        },
        testCases = listOf(
            Identifier("a"),
            Identifier("value"),
            Identifier("⛄️"),
        ).map { arg ->
            TestCase(
                input = Application(
                    function = Identifier("id"),
                    argument = arg,
                ),
                output = arg,
            )
        }
    )
    val context = LocalContext.current
    LambdaTheme {
        Surface {
            ExerciseScreen(exercise = exercise) {
                Toast.makeText(context, it.prettyPrint(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
