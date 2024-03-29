package org.simonolander.lambda.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.content.exercise.booleans.andExercise
import org.simonolander.lambda.domain.Exercise
import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.engine.ParserException
import org.simonolander.lambda.engine.parse
import org.simonolander.lambda.ui.theme.Lambda
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.theme.codeStyle
import org.simonolander.lambda.ui.view.scrollableNoFling

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
            .scrollableNoFling(scrollState)
            .verticalScroll(scrollState)
    ) {
        Text(
            text = exercise.name,
            style = MaterialTheme.typography.displayMedium,
        )
        Text(
            text = exercise.description,
            style = MaterialTheme.typography.titleMedium,
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
                        imageVector = Icons.Filled.Lambda,
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
                    color = MaterialTheme.colorScheme.error,
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
                style = MaterialTheme.typography.titleMedium,
            )

            exercise.library.forEach { (name, expr) ->
                Row(Modifier.fillMaxWidth()) {
                    Text(
                        text = AnnotatedString(name, codeStyle),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.weight(1f)
                    )

                    Text(
                        text = AnnotatedString(expr.prettyPrint(), codeStyle),
                        style = MaterialTheme.typography.titleMedium,
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
