package org.simonolander.lambda.data

import androidx.compose.runtime.Composable

typealias LevelViewComposable = @Composable (onLevelComplete: () -> Unit) -> Unit
