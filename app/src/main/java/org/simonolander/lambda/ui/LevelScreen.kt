package org.simonolander.lambda.ui

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import org.simonolander.lambda.data.Level
import org.simonolander.lambda.data.LevelId
import org.simonolander.lambda.ui.levels.C1L1
import org.simonolander.lambda.ui.levels.C1L2
import org.simonolander.lambda.ui.levels.C1L3
import org.simonolander.lambda.ui.levels.C1L4

@Composable
fun LevelScreen(levelId: LevelId, onLevelCompleted: (LevelId) -> Unit) {
    val level = Level.findById(levelId) ?: return LevelNotFound(levelId)

    when (level) {
        Level.C1L1 -> C1L1 {
            onLevelCompleted(level.id)
        }
        Level.C1L2 -> C1L2 {
            onLevelCompleted(level.id)
        }
        Level.C1L3 -> C1L3 {
            onLevelCompleted(level.id)
        }
        Level.C1L4 -> C1L4 {
            onLevelCompleted(level.id)
        }
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
