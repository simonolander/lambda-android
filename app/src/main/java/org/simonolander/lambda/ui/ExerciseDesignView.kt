package org.simonolander.lambda.ui

import android.widget.Toast
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.content.exercise.booleans.andExercise
import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.engine.ParserException
import org.simonolander.lambda.engine.parse
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.theme.codeStyle

@Composable
fun ExerciseDesignView(exercise: Exercise, onSubmit: (Expression) -> Unit) {
    var solutionValue by remember {
        mutableStateOf(TextFieldValue())
    }
    var parserException: ParserException? by remember {
        mutableStateOf(null)
    }

    val scrollState = rememberScrollState()

    fun validateAndSubmit() {
        try {
            val expression = parse(solutionValue.text)
            onSubmit(expression)
        }
        catch (e: ParserException) {
            parserException = e
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .scrollable(scrollState, Orientation.Vertical)
            .verticalScroll(scrollState)
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
            label = { Text(text = exercise.functionName) },
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

        if (exercise.library.isNotEmpty()) {
            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "The following library functions are available to you:",
                style = MaterialTheme.typography.subtitle1,
            )

            exercise.library.forEach { (name, expr) ->
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = AnnotatedString(name, codeStyle),
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = AnnotatedString(expr.prettyPrint(), codeStyle),
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.weight(2f)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    val context = LocalContext.current
    LambdaTheme {
        Surface {
            ExerciseDesignView(exercise = andExercise) {
                Toast.makeText(context, it.prettyPrint(), Toast.LENGTH_SHORT).show()
            }
        }
    }
}
