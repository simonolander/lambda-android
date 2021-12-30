package org.simonolander.lambda.ui.levels

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import org.simonolander.lambda.data.DialogBuilder
import org.simonolander.lambda.data.Message
import org.simonolander.lambda.misc.lambda
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.view.DialogView

private val helloDialog = run {
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
        .message("Hello! 👋") {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "👋",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.h1
                )
            }
        }
        .message("Welcome to Lambda!")
        .message("I will be your guide here, as we go through the wondrous world of $lambdaCalculus. $lambda is a Greek symbol pronounced lambda. Most of the time, I'll write $lambdaCalculus and not lambda calculus.")
        .message("First off, how familiar are you with $lambdaCalculus?")
        .question(
            "How familiar are you with $lambdaCalculus?",
            "Not at all" to notAtAll,
            "Somewhat familiar" to somewhatFamiliar,
            "Quite familiar" to quiteFamiliar,
        )
}

@Composable
fun HelloView(onLevelComplete: () -> Unit) {
    val (dialog, setDialog) = remember {
        mutableStateOf(helloDialog)
    }

    val (view, setView) = remember {
        mutableStateOf((dialog as? Message)?.view)
    }

    Column(modifier = Modifier.fillMaxSize()) {
        Box(Modifier.weight(1f)) {
            view?.invoke()
        }
        Box {
            DialogView(
                dialog = dialog,
                animationSpeed = 30f,
                onNextDialog = {
                    if (it != null) {
                        setDialog(it)
                        setView((it as? Message)?.view ?: view)
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
            HelloView {
                Toast.makeText(
                    context,
                    "Called onLevelComplete",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
