package com.example.testprep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.testprep.ui.navigation.Routes
import com.example.testprep.ui.theme.TestPrepTheme
import com.example.testprep.ui.screens.MenuScreen
import com.example.testprep.ui.screens.ImportScreen
import com.example.testprep.ui.screens.TestScreen
import com.example.testprep.ui.screens.TrainingScreen
import com.example.testprep.ui.screens.MistakesScreen
import com.example.testprep.ui.screens.StatsScreen
import com.example.testprep.ui.screens.ResultScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            App()
        }
    }
}

sealed class Screen(val route: String) {
    data object Menu : Screen("menu")
    data object Test : Screen("test")
    data object Training : Screen("training")
    data object Stats : Screen("stats")
    data object Mistakes : Screen("mistakes")
}

@Composable
fun App() {
    val navController = rememberNavController()
    TestPrepTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            NavHost(navController = navController, startDestination = Routes.MENU) {
                composable(Routes.MENU) { MenuScreen(navController) }
                composable(Routes.IMPORT) { ImportScreen(navController) }
                composable(Routes.TEST) { TestScreen(navController) }
                composable(Routes.TRAINING) { TrainingScreen(navController) }
                composable(Routes.MISTAKES) { MistakesScreen(navController) }
                composable(Routes.STATS) { StatsScreen(navController) }
                composable(Routes.RESULT) { backStackEntry ->
                    ResultScreen(navController, backStackEntry)
                }
            }
        }
    }
}

