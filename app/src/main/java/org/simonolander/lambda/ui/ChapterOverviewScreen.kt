package org.simonolander.lambda.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.database.LambdaDatabase
import org.simonolander.lambda.content.Chapter
import org.simonolander.lambda.content.Level
import org.simonolander.lambda.domain.LevelId
import org.simonolander.lambda.ui.theme.LambdaTheme

@ExperimentalMaterial3Api
@Composable
fun ChapterOverviewScreen(
    chapters: Array<Chapter> = Chapter.values(),
    onNavigateLevel: (LevelId) -> Unit = {},
) {
    val listState = rememberLazyListState()
    val context = LocalContext.current
    val db = remember {
        LambdaDatabase.getInstance(context)
    }

    LazyColumn(
        state = listState,
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp),
    ) {
        items(chapters) { chapter ->
            ChapterView(chapter) {
                onNavigateLevel(it.id)
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
private fun ChapterView(chapter: Chapter, onLevelClick: (Level) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = chapter.title,
            style = MaterialTheme.typography.displayMedium,
        )
        chapter.levels.forEach { level ->
            LevelView(
                level = level,
                onClick = { onLevelClick(level) },
            )
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun LevelView(level: Level, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .background(MaterialTheme.colorScheme.primary),
        shape = MaterialTheme.shapes.small,
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = level.title,
                style = MaterialTheme.typography.titleLarge,
            )
            Text(
                text = "Not completed",
                fontStyle = FontStyle.Italic,
            )
        }
    }
}

@ExperimentalMaterial3Api
@Preview
@Composable
private fun DefaultPreview() {
    LambdaTheme {
        Surface {
            ChapterOverviewScreen()
        }
    }
}
