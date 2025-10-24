package com.example.testprep.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController

@Composable
fun TestScreen(navController: NavHostController) {
    QuizScreen(navController, com.example.testprep.ui.viewmodel.QuizMode.TEST)
}

@Composable
fun EmptyState(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Нет вопросов. Импортируйте файл в меню 'ИМПОРТ ВОПРОСОВ'.")
        Button(onClick = { navController.popBackStack() }) { Text("В меню") }
    }
}
