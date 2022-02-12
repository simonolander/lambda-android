package org.simonolander.lambda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.launch
import org.simonolander.lambda.content.Chapter
import org.simonolander.lambda.content.Level
import org.simonolander.lambda.database.Event
import org.simonolander.lambda.database.LambdaDatabase
import org.simonolander.lambda.database.Solution
import org.simonolander.lambda.domain.ChapterId
import org.simonolander.lambda.domain.EventType
import org.simonolander.lambda.domain.LevelId
import org.simonolander.lambda.engine.Expression
import org.simonolander.lambda.ui.screen.ChapterScreen
import org.simonolander.lambda.ui.screen.ChaptersScreen
import org.simonolander.lambda.ui.screen.LevelScreen
import org.simonolander.lambda.ui.theme.LambdaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LambdaApp()
        }
    }
}

@Composable
fun LambdaApp() {
    LambdaTheme {
        val navController = rememberNavController()
        val backstackEntry = navController.currentBackStackEntryAsState()
        Scaffold {
            LambdaNavHost(navController, Modifier.padding(it))
        }
    }
}

@Composable
fun LambdaNavHost(navController: NavHostController, modifier: Modifier) {
    val database = LambdaDatabase.getInstance(LocalContext.current)
    val eventDao = database.eventDao()
    val coroutineScope = rememberCoroutineScope()
    val solutions by database.solutionDao().getAll().collectAsState(emptyList())
    val completedLevelsIds by derivedStateOf {
        solutions.map { LevelId(it.levelId) }.toSet()
    }
    NavHost(
        navController = navController,
        startDestination = "chapters",
        modifier = modifier,
    ) {
        composable(route = "chapters") {
            ChaptersScreen(
                chapters = Chapter.values().toList(),
                completedLessonIds = completedLevelsIds,
                onChapterClicked = { chapterId ->
                    navController.navigate("chapters/$chapterId")
                },
            )
        }

        composable(
            route = "chapters/{chapterId}",
            arguments = listOf(
                navArgument("chapterId") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val chapterId = backStackEntry.arguments
                ?.getString("chapterId")
                ?.let { ChapterId(it) }
                ?: throw IllegalStateException("Missing navArgument: chapterId")

            ChapterScreen(
                chapterId = chapterId,
                completedLevelIds = completedLevelsIds,
                onLevelClick = { levelId ->
                    navController.navigate("levels/$levelId")
                }
            )
        }

        composable(
            route = "levels/{levelId}",
            arguments = listOf(
                navArgument("levelId") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val levelId = backStackEntry.arguments
                ?.getString("levelId")
                ?.let { LevelId(it) }
                ?: throw IllegalStateException("Missing navArgument: levelId")

            val onLevelCompleted: (Expression?) -> Unit = { answer ->
                coroutineScope.launch {
                    database.solutionDao().insert(
                        Solution(
                            levelId = levelId.value,
                            value = answer?.prettyPrint(),
                        )
                    )
                }
            }

            val onNavigateToNextLevel = {
                val nextLevelId = Level.nextLevel(levelId)?.id
                if (nextLevelId == null || Level.isLastInChapter(levelId)) {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo("chapters", true)
                        .build()
                    navController.navigate("chapters", navOptions)
                }
                else {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo("chapters", false)
                        .build()
                    navController.navigate("levels/$nextLevelId", navOptions)
                }
            }

            LevelScreen(
                levelId = levelId,
                onLevelCompleted = onLevelCompleted,
                onNavigateToNextLevel = onNavigateToNextLevel,
                onParseError = suspend {
                    val parseErrorEventsExist = eventDao
                        .hasEvent(type = EventType.ParseError)
                        .firstOrNull()
                        ?: false
                    if (parseErrorEventsExist) {
                        false
                    }
                    else {
                        eventDao.insert(Event(type = EventType.ParseError))
                        true
                    }
                }
            )
        }
    }
}
