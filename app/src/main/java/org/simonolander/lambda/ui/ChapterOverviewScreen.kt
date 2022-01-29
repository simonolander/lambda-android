package org.simonolander.lambda.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.simonolander.lambda.database.LambdaDatabase
import org.simonolander.lambda.domain.Chapter
import org.simonolander.lambda.domain.Level
import org.simonolander.lambda.domain.LevelId
import org.simonolander.lambda.ui.theme.LambdaTheme

@ExperimentalMaterialApi
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

@ExperimentalMaterialApi
@Composable
private fun ChapterView(chapter: Chapter, onLevelClick: (Level) -> Unit) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = chapter.title,
            style = MaterialTheme.typography.h2,
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

@ExperimentalMaterialApi
@Composable
fun LevelView(level: Level, onClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.small,
        onClick = onClick,
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Column(
            modifier = Modifier.padding(10.dp)
        ) {
            Text(
                text = level.title,
                style = MaterialTheme.typography.h6,
            )
            Text(
                text = "Not completed",
                fontStyle = FontStyle.Italic,
            )
        }
    }
}

@ExperimentalMaterialApi
@Preview
@Composable
private fun DefaultPreview() {
    LambdaTheme {
        Surface {
            ChapterOverviewScreen()
        }
    }
}
