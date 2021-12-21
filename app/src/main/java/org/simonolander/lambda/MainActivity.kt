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
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import org.simonolander.lambda.data.Level
import org.simonolander.lambda.data.LevelId
import org.simonolander.lambda.ui.ChapterOverviewScreen
import org.simonolander.lambda.ui.LevelScreen
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

        composable("level/{levelId}") { backStackEntry ->
            val levelId = backStackEntry.arguments!!
                .getString("levelId")!!
                .let { LevelId(it) }
            LevelScreen(levelId) {
                val nextLevel = Level.nextLevel(it)
                if (nextLevel == null) {
                    navController.navigate("overview")
                }
                else {
                    navController.navigate("level/${nextLevel.id.value}")
                }
            }
        }
    }
}

@Preview(name = "light mode", showBackground = true, uiMode = UI_MODE_NIGHT_NO)
@Composable
fun DefaultPreview() {
    ChapterOverviewScreen()
}
