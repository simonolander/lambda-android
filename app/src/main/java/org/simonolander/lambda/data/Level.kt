package org.simonolander.lambda.data

import androidx.compose.runtime.Composable

data class Level(
    val id: LevelId,
    val name: String,
    val screen: @Composable () -> Unit,
)
