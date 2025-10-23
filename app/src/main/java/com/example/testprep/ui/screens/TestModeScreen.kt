package com.example.testprep.ui.screens

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun TestScreen(navController: NavHostController) {
    QuizScreen(navController, com.example.testprep.ui.viewmodel.QuizMode.TEST)
}
