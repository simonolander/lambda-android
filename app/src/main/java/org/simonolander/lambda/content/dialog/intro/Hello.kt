package org.simonolander.lambda.content.dialog.intro

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.ui.theme.LambdaTheme

val helloDialog = run {
    val notAtAll = DialogBuilder()
        .message("Cool!")
        .message("The λ in $lambdaCalculus is a Greek symbol called lambda.")
        .message("Most of the time, I'll say $lambdaCalculus and not lambda calculus.")
        .message("You'll get a hang of things in no time!")
        .build()

    val somewhatFamiliar = DialogBuilder()
        .message("Alright! You're in for a treat.")
        .message("Think of the initial lessons as a refresher.")
        .build()

    val quiteFamiliar = DialogBuilder()
        .message("Great!")
        .message("In that case, the first few lessons will be a breeze.")
        .message("I hope you'll find the game entertaining.")
        .build()

    DialogBuilder()
        .message("Hello!") { Wave() }
        .message("I'm Lambert, welcome to my lecture on $lambdaCalculus!")
        .message("First off, how familiar are you with $lambdaCalculus?")
        .question(
            "How familiar are you with $lambdaCalculus?",
            "Not at all" to notAtAll,
            "Somewhat familiar" to somewhatFamiliar,
            "Quite familiar" to quiteFamiliar,
        )
}

@Composable
private fun BoxScope.Wave() {
    Text(
        text = "👋",
        modifier = Modifier.align(Alignment.Center),
        style = MaterialTheme.typography.displayMedium
    )
}

@Preview
@Composable
private fun WavePreview() {
    Surface {
        LambdaTheme {
            Box {
                Wave()
            }
        }
    }
}
