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
import kotlinx.coroutines.launch
import org.simonolander.lambda.database.LambdaDatabase
import org.simonolander.lambda.database.Solution
import org.simonolander.lambda.domain.Chapter
import org.simonolander.lambda.domain.ChapterId
import org.simonolander.lambda.domain.Level
import org.simonolander.lambda.domain.LevelId
import org.simonolander.lambda.ui.LevelScreen
import org.simonolander.lambda.ui.screen.ChapterScreen
import org.simonolander.lambda.ui.screen.ChaptersScreen
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
                    navController.navigate("chapters/${chapterId.value}")
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
                    navController.navigate("levels/${levelId.value}")
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

            LevelScreen(
                levelId = levelId,
                onLevelCompleted = { answer ->
                    coroutineScope.launch {
                        database.solutionDao().insert(
                            Solution(
                                levelId = levelId.value,
                                value = answer?.prettyPrint(),
                            )
                        )
                    }
                    val nextLevel = Level.nextLevel(levelId)
                    if (nextLevel == null || Level.isLastInChapter(levelId)) {
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo("chapters", true)
                            .build()
                        navController.navigate("chapters", navOptions)
                    }
                    else {
                        val navOptions = NavOptions.Builder()
                            .setPopUpTo("chapters", false)
                            .build()
                        navController.navigate("levels/${nextLevel.id.value}", navOptions)
                    }
                }
            )
        }
    }
}
