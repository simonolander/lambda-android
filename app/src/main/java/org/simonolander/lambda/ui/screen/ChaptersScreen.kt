package org.simonolander.lambda.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.content.Chapter
import org.simonolander.lambda.content.Level
import org.simonolander.lambda.domain.ChapterId
import org.simonolander.lambda.domain.LevelId
import org.simonolander.lambda.ui.theme.LambdaTheme
import org.simonolander.lambda.ui.view.scrollableNoFling

@Composable
fun ChaptersScreen(
    chapters: List<Chapter>,
    completedLessonIds: Set<LevelId>,
    onChapterClicked: (ChapterId) -> Unit,
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .scrollableNoFling(scrollState)
            .verticalScroll(scrollState)
            .padding(8.dp),
    ) {
        Text(
            text = "Chapters",
            style = MaterialTheme.typography.displayMedium,
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
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
        ) {
            val numberOfCompletedLessons = chapter.levels.count { it.id in completedLessonIds }
            val totalNumberOfLessons = chapter.levels.size
            Column {
                Text(
                    text = chapter.title,
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = "$numberOfCompletedLessons out of $totalNumberOfLessons lessons completed",
                    style = MaterialTheme.typography.bodySmall
                )
            }
            if (numberOfCompletedLessons == totalNumberOfLessons) {
                Icon(
                    imageVector = Icons.Default.Star,
                    contentDescription = "Chapter complete",
                )
            }
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
