package org.simonolander.lambda.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.flowlayout.FlowRow
import org.simonolander.lambda.data.Chapter
import org.simonolander.lambda.data.Level
import org.simonolander.lambda.data.LevelId
import org.simonolander.lambda.ui.theme.LambdaTheme

@Composable
fun ChapterOverviewScreen(chapters: Array<Chapter> = Chapter.values(), onNavigateLevel: (LevelId) -> Unit = {}) {
    val listState = rememberLazyListState()
    LazyColumn(
        state = listState,
        modifier = Modifier.fillMaxSize()
            .padding(8.dp),
    ) {
        items(chapters) { chapter ->
            ChapterView(chapter) {
                onNavigateLevel(it.id)
            }
        }
    }
}

@Composable
private fun ChapterView(chapter: Chapter, onLevelClick: (Level) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = chapter.title,
            style = MaterialTheme.typography.h2,
        )
        FlowRow(
            crossAxisSpacing = 10.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            chapter.levels.forEach {
                LevelView(it) {
                    onLevelClick(it)
                }
            }
        }
    }
}

@Composable
fun LevelView(level: Level, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier.padding(end = 10.dp)
    ) {
        Icon(
            imageVector = Icons.Filled.Star,
            contentDescription = "completed",
            modifier = Modifier.size(ButtonDefaults.IconSize)
        )
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    LambdaTheme {
        Surface {
            ChapterOverviewScreen()
        }
    }
}
