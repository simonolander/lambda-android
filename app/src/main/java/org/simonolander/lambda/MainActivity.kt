package org.simonolander.lambda

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import org.simonolander.lambda.data.Level
import org.simonolander.lambda.data.LevelId
import org.simonolander.lambda.engine.parse
import org.simonolander.lambda.ui.*
import org.simonolander.lambda.ui.theme.LambdaTheme

@ExperimentalMaterialApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LambdaApp()
        }
    }
}

@ExperimentalMaterialApi
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

@ExperimentalMaterialApi
@Composable
fun LambdaNavHost(navController: NavHostController, modifier: Modifier) {
    NavHost(
        navController = navController,
        startDestination = "overview",
        modifier = modifier,
    ) {
        composable("overview") {
            ChapterOverviewScreen {
                navController.navigate("level/${it.value}")
            }
        }

        composable(
            route = "level/{levelId}",
            arguments = listOf(
                navArgument("levelId") { type = NavType.StringType },
            ),
        ) { backStackEntry ->
            val levelId = backStackEntry.arguments!!
                .getString("levelId")!!
                .let { LevelId(it) }
            LevelScreen(levelId) {
                val nextLevel = Level.nextLevel(it)
                if (nextLevel == null) {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo("overview", true)
                        .build()
                    navController.navigate("overview", navOptions)
                }
                else {
                    val navOptions = NavOptions.Builder()
                        .setPopUpTo("overview", false)
                        .build()
                    navController.navigate("level/${nextLevel.id.value}", navOptions)
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
@Preview(name = "light mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
fun DefaultPreview() {
    ChapterOverviewScreen()
}
