package org.simonolander.lambda.ui.screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.domain.Chapter
import org.simonolander.lambda.domain.ChapterId
import org.simonolander.lambda.domain.Level
import org.simonolander.lambda.domain.LevelId
import org.simonolander.lambda.ui.theme.LambdaTheme

@Composable
fun ChapterScreen(
    chapterId: ChapterId,
    completedLevelIds: Set<LevelId>,
    onLevelClick: (LevelId) -> Unit,
) {
    val chapter = Chapter.findById(chapterId)

    if (chapter == null) {
        ChapterNotFoundView(chapterId)
    }
    else {
        ChapterView(
            chapter = chapter,
            completedLevelIds = completedLevelIds,
            onLevelClick = onLevelClick,
        )
    }
}

@Composable
fun ChapterView(
    chapter: Chapter,
    completedLevelIds: Set<LevelId>,
    onLevelClick: (LevelId) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .scrollable(scrollState, Orientation.Vertical)
            .verticalScroll(scrollState)
            .padding(8.dp)
    ) {
        Text(
            text = chapter.title,
            style = MaterialTheme.typography.h2,
        )
        chapter.levels.forEach { level ->
            LevelCardView(
                level = level,
                completed = level.id in completedLevelIds,
                onClick = { onLevelClick(level.id) },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun LevelCardView(level: Level, completed: Boolean, onClick: () -> Unit) {
    Card(onClick = onClick) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text(
                text = level.title,
                style = MaterialTheme.typography.h6,
            )

            if (completed) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = "Lesson completed"
                )
            }
        }
    }
}

@Composable
fun ChapterNotFoundView(chapterId: ChapterId) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = "Not found",
            style = MaterialTheme.typography.h2,
        )
        Text(
            text = "You attempted to view chapter $chapterId, but no such chapter exists.",
            style = MaterialTheme.typography.caption,
        )
    }
}

@Preview
@Composable
private fun ChapterScreenPreview() {
    Surface {
        LambdaTheme {
            ChapterScreen(
                chapterId = Chapter.BASIC_EXERCISES.id,
                completedLevelIds = Level.values()
                    .filter { it.ordinal % 3 != 0 }
                    .map { it.id }
                    .toSet(),
                onLevelClick = {},
            )
        }
    }
}

@Preview
@Composable
private fun ChapterNotFoundScreenPreview() {
    Surface {
        LambdaTheme {
            ChapterScreen(
                chapterId = ChapterId("ad92k3"),
                completedLevelIds = Level.values()
                    .filter { it.ordinal % 3 != 0 }
                    .map { it.id }
                    .toSet(),
                onLevelClick = {},
            )
        }
    }
}
