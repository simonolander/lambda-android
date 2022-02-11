package org.simonolander.lambda.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.simonolander.lambda.content.Level
import org.simonolander.lambda.domain.LevelId
import org.simonolander.lambda.engine.Expression

@Composable
fun LevelScreen(levelId: LevelId, onLevelCompleted: (Expression?) -> Unit) {
    Level.findById(levelId)?.view?.invoke(onLevelCompleted)
        ?: LevelNotFound(levelId)
}

@Composable
fun LevelNotFound(levelId: LevelId) {
    Text(
        text = "Level not found: $levelId",
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.error
    )
}
