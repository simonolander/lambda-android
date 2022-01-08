package org.simonolander.lambda.domain

import androidx.compose.runtime.Composable
import org.simonolander.lambda.engine.Expression

typealias LevelViewComposable = @Composable (onLevelComplete: (Expression?) -> Unit) -> Unit
