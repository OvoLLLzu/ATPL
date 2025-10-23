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
import com.example.testprep.ui.navigation.Routes

@Composable
fun MenuScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(onClick = { navController.navigate(Routes.TEST) }) { Text("ТЕСТ") }
        Button(onClick = { navController.navigate(Routes.TRAINING) }) { Text("ТРЕНИРОВКА") }
        Button(onClick = { navController.navigate(Routes.STATS) }) { Text("СТАТИСТИКА") }
        Button(onClick = { navController.navigate(Routes.MISTAKES) }) { Text("РАБОТА НАД ОШИБКАМИ") }
        Button(onClick = { navController.navigate(Routes.IMPORT) }) { Text("ИМПОРТ ВОПРОСОВ") }
    }
}
