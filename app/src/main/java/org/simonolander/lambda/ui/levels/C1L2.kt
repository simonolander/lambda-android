package org.simonolander.lambda.ui.levels

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import org.simonolander.lambda.R

@Composable
fun C1L2(onLevelComplete: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onLevelComplete() }
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
