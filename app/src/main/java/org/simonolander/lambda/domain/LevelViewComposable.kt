package org.simonolander.lambda.domain

import androidx.compose.runtime.Composable

typealias LevelViewComposable = @Composable (onLevelComplete: () -> Unit) -> Unit
