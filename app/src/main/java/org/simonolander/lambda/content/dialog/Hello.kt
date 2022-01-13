package org.simonolander.lambda.content.dialog

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.misc.lambdaCalculus

val helloDialog = run {
    val notAtAll = DialogBuilder()
        .message("Cool!")
        .message("I hope I'll be able to give you a nice introduction.")
        .message("You'll get a hang of things in no time!")
        .build()

    val somewhatFamiliar = DialogBuilder()
        .message("Alright! You're in for a treat.")
        .message("Think of the initial lessons as a refresher.")
        .build()

    val quiteFamiliar = DialogBuilder()
        .message("Great!")
        .message("In that case, the first few lessons will be a breeze.")
        .message("I hope you'll find the game entertaining regardless.")
        .build()

    DialogBuilder()
        .message("Hello!") {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = "👋",
                    modifier = Modifier.align(Alignment.Center),
                    style = MaterialTheme.typography.h2
                )
            }
        }
        .message("Welcome to Lambda!")
        .message("I will be your guide here, as we go through the wondrous world of $lambdaCalculus.")
        .message("λ is a Greek symbol pronounced lambda. Most of the time, I'll write $lambdaCalculus and not lambda calculus.")
        .message("First off, how familiar are you with $lambdaCalculus?")
        .question(
            "How familiar are you with $lambdaCalculus?",
            "Not at all" to notAtAll,
            "Somewhat familiar" to somewhatFamiliar,
            "Quite familiar" to quiteFamiliar,
        )
}
