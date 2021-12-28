package org.simonolander.lambda.ui.levels

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.data.DialogBuilder
import org.simonolander.lambda.data.Exercise
import org.simonolander.lambda.data.andExercise
import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.ui.ExecutionState
import org.simonolander.lambda.ui.ExecutionView
import org.simonolander.lambda.ui.ExerciseView
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.view.DialogView

private val helloWorldDialog = run {
    val notAtAll = DialogBuilder()
        .message("Great!")
        .message("I hope I'll be able to give you a nice introduction.")
        .message("You'll get a hang of things in no time!")
        .build()

    val somewhatFamiliar = DialogBuilder()
        .message("Alright! You're in for a treat.")
        .message("Think of the initial lessons as a refresher.")
        .build()

    val quiteFamiliar = DialogBuilder()
        .message("Cool!")
        .message("In that case, the first few lessons will be a breeze.")
        .message("I hope you'll find the game entertaining regardless.")
        .build()

    DialogBuilder()
        .message("Hello!")
        .message("Welcome to Lambda!")
        .message("I will be your guide here, as we go through the wondrous world of lambda calculus.")
        .message("First off, how familiar are you with lambda calculus?")
        .question(
            "How familiar are you with lambda calculus?",
            "Not at all" to notAtAll,
            "Somewhat familiar" to somewhatFamiliar,
            "Quite familiar" to quiteFamiliar,
        )
}

@Composable
fun HelloWorldView(onLevelComplete: () -> Unit) {
    val (dialog, setDialog) = remember {
        mutableStateOf(helloWorldDialog)
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Box(modifier = Modifier.align(Alignment.BottomCenter)) {
            DialogView(
                dialog = dialog,
                animationSpeed = 30f,
                onNextDialog = {
                    if (it != null) {
                        setDialog(it)
                    }
                    else {
                        onLevelComplete()
                    }
                }
            )
        }
    }
}

@Composable
@Preview
private fun LevelViewPreview() {
    val context = LocalContext.current
    Surface {
        LambdaTheme {
            HelloWorldView {
                Toast.makeText(
                    context,
                    "Called onLevelComplete",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
