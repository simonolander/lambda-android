package org.simonolander.lambda.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.simonolander.lambda.domain.Level
import org.simonolander.lambda.domain.LevelId

@Composable
fun LevelScreen(levelId: LevelId, onLevelCompleted: (LevelId) -> Unit) {
    val level = Level.findById(levelId) ?: return LevelNotFound(levelId)
    level.view {
        onLevelCompleted(level.id)
    }
}

@Composable
fun LevelNotFound(levelId: LevelId) {
    Text(
        text = "Level not found: $levelId",
        style = MaterialTheme.typography.h2,
        color = MaterialTheme.colors.error
    )
}
