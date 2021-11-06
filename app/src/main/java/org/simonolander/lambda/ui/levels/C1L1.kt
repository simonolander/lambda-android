package org.simonolander.lambda.ui.levels

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign

@Composable
fun C1L1(onClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .clickable { onClick() }
    )
    {
        Text(
            text = "λx. x",
            style = MaterialTheme.typography.h2,
            textAlign = TextAlign.Center,
        )
        Text(
            text = "This is a function",
            style = MaterialTheme.typography.subtitle1,
            textAlign = TextAlign.Center,
        )
    }
}
