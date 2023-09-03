package org.simonolander.lambda.content.dialog.pair

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.domain.DialogBuilder
import org.simonolander.lambda.engine.PAIR
import org.simonolander.lambda.engine.parse
import org.simonolander.lambda.misc.nonBreaking
import org.simonolander.lambda.misc.pair

val pairsDialog = run {
    val dividend = 17
    val divisor = 3
    val quotient = dividend / divisor
    val remainder = dividend % divisor
    val pairOrNumbers = pair(a = quotient, b = remainder)
    val pairOfBooleans = pair(a = true, b = false)
    val pairFunction = PAIR.second.prettyPrint().nonBreaking()
    DialogBuilder()
        .message("Hello! In this chapter we'll be exploring our first data structure: the pair.") {
            LargePair()
        }
        .message("A pair is a container of two values, like $pairOrNumbers or $pairOfBooleans.")
        .message("They are super useful if we want to return multiple values from a function.")
        .message("For example, say we want to design a function for division with remainder.")
        .message("Dividing $dividend by $divisor would give us the quotient $quotient, and the remainder $remainder, which we could return as $pairOrNumbers")
        .message("We design the pair function like this: $pairFunction.") {
            PairsDefinition()
        }
        .message("The inputs a and b are the values we want to store in our pair.")
        .message("The input f is a function that lets us access the values stored in our pair.")
        .message("Take a moment to understand the pair function before we move on!")
        .build()
}

@Preview
@Composable
private fun LargePair() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        Text(
            text = pair('a', 'b'),
            style = MaterialTheme.typography.displayMedium,
        )
    }
}

@Preview
@Composable
private fun PairsDefinition() {
    Column(
        modifier = Modifier.padding(8.dp)
    ) {
        Text(
            text = "The pair function",
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = PAIR.second.prettyPrint(),
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "The pair " + pair(5, 2),
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = parse("λf. f 5 2").prettyPrint(),
        )
    }
}
