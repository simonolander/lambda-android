package org.simonolander.lambda.content.dialog.numbers

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.engine.churchNumeral
import org.simonolander.lambda.misc.lambdaCalculus
import org.simonolander.lambda.misc.nonBreaking

val naturalNumbersDialog = run {
    val zero = "λ f x. x".nonBreaking()
    val six = churchNumeral(6).second.prettyPrint().nonBreaking()
    val zeroIsFalse = DialogBuilder()
        .message("You may have noticed that zero is the same function as false!")
        .message("That turns out to be occasionally useful.")
        .message("In the coming lessons we're going to see how to do some mathematics!")
        .build()
    val questionZero = DialogBuilder()
        .message("How do you think we write zero?")
        .question(
            "Which one of these is zero?",
            zero to
                DialogBuilder()
                    .message("Yes, correct!")
                    .build(zeroIsFalse),
            "λ x. x" to
                DialogBuilder()
                    .message("Almost! We write zero as $zero. It's important that we have both parameters, even if don't use the f.")
                    .build(zeroIsFalse),
            "λ f x. f" to
                DialogBuilder()
                    .message("Almost! We write zero as $zero. f is applied to x zero times, which is just x.")
                    .build(zeroIsFalse),
        )
    DialogBuilder()
        .message("Hello! This chapter is all about numbers.")
        .message("In particular, it's about natural numbers. You know, 0, 1, 2, 3 … and so forth. No -1, 6.28, τ, or ∞.")
        .message("First, we'll look at how to construct these numbers in $lambdaCalculus.")
        .message("We're going to use what's called Church numerals. It's all about repeated function application.")
        .message("Have a look at these examples.") {
            ChurchNumeralExample()
        }
        .message("You probably see a pattern.")
        .message("The number 1 we've seen before. It's the apply function that you designed earlier.")
        .message("The number 2 is like apply, but we apply f two times, and so forth.")
        .message("Small test! How do we write the number 6?")
        .question(
            "Which one of these functions are 6?",
            churchNumeral(5).second.prettyPrint() to
                DialogBuilder()
                    .message("That's 5!")
                    .message("6 is $six. In order to determine which number it is, we count the number of f's in the body.")
                    .build(questionZero),
            six to
                DialogBuilder()
                    .message("Yes, well done! ")
                    .message("In order to determine which number it is, we just count the number of f's in the body.")
                    .build(questionZero),
            churchNumeral(7).second.prettyPrint() to
                DialogBuilder()
                    .message("That's 7!")
                    .message("6 is $six. In order to determine which number it is, we count the number of f's in the body.")
                    .build(questionZero),
        )
}

@Preview
@Composable
private fun ChurchNumeralExample() {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(
                text = "Number",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(1f),
            )
            Text(
                text = "Church numeral",
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.weight(5f),
            )
        }
        (1..5).forEach { n ->
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(
                    text = "$n",
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(1f),
                )
                Text(
                    text = churchNumeral(n).second.prettyPrint(),
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(5f),
                )
            }
        }
    }
}
