package org.simonolander.lambda.ui.screen

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.domain.Chapter
import org.simonolander.lambda.domain.ChapterId
import org.simonolander.lambda.domain.Level
import org.simonolander.lambda.domain.LevelId
import org.simonolander.lambda.ui.theme.LambdaTheme

@Composable
fun ChaptersScreen(
    chapters: List<Chapter>,
    completedLessonIds: Set<LevelId>,
    onChapterClicked: (ChapterId) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .scrollable(scrollState, Orientation.Vertical)
            .verticalScroll(scrollState),
    ) {
        Text(
            text = "Chapters",
            style = MaterialTheme.typography.h2,
        )
        chapters.forEach { chapter ->
            ChapterCardView(
                chapter = chapter,
                completedLessonIds = completedLessonIds,
                onClick = { onChapterClicked(chapter.id) },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
private fun ChapterCardView(
    chapter: Chapter,
    completedLessonIds: Set<LevelId>,
    onClick: () -> Unit,
) {
    Card(
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            Text(
                text = chapter.title,
                style = MaterialTheme.typography.h6,
            )
            val numberOfCompletedLessons = chapter.levels.count { it.id in completedLessonIds }
            val totalNumberOfLessons = chapter.levels.size
            Text(
                text = "$numberOfCompletedLessons out of $totalNumberOfLessons lessons completed",
                style = MaterialTheme.typography.caption
            )
        }
    }
}

@Preview
@Composable
private fun ChapterCardViewPreview() {
    ChapterCardView(
        chapter = Chapter.INTRODUCTION,
        completedLessonIds = emptySet(),
        onClick = {},
    )
}

@Preview
@Composable
private fun ChaptersScreenPreview() {
    Surface {
        LambdaTheme {
            ChaptersScreen(
                chapters = Chapter.values().toList(),
                completedLessonIds = Level.values()
                    .take(11)
                    .map { it.id }
                    .toSet(),
            ) {}
        }
    }
}
