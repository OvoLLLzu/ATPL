package com.example.testprep.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import com.example.testprep.ui.navigation.Routes
import com.example.testprep.ui.viewmodel.QuizMode
import com.example.testprep.ui.viewmodel.QuizViewModel
import com.example.testprep.ui.viewmodel.activityViewModel

@Composable
fun TrainingScreen(navController: NavHostController) {
    QuizScreen(navController, QuizMode.TRAINING)
}

@Composable
fun MistakesScreen(navController: NavHostController) {
    QuizScreen(navController, QuizMode.MISTAKES)
}

@Composable
fun QuizScreen(navController: NavHostController, mode: QuizMode) {
    val vm: QuizViewModel = activityViewModel()
    val state by vm.state.collectAsState()

    LaunchedEffect(mode) { vm.start(mode) }

    if (state.questions.isEmpty()) {
        EmptyState(navController)
        return
    }

    val q = state.questions[state.currentIndex]
    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = "Вопрос ${state.currentIndex + 1} из ${state.questions.size}", style = MaterialTheme.typography.bodyMedium)
        Text(text = q.questionText, style = MaterialTheme.typography.titleLarge)
        val answered = state.selectedIndex != null
        OptionTrain(index = 0, text = q.option1, selected = state.selectedIndex == 0, correct = q.correctIndex == 0, showFeedback = mode != QuizMode.TEST, answered = answered) { vm.selectAnswer(0) }
        OptionTrain(index = 1, text = q.option2, selected = state.selectedIndex == 1, correct = q.correctIndex == 1, showFeedback = mode != QuizMode.TEST, answered = answered) { vm.selectAnswer(1) }
        OptionTrain(index = 2, text = q.option3, selected = state.selectedIndex == 2, correct = q.correctIndex == 2, showFeedback = mode != QuizMode.TEST, answered = answered) { vm.selectAnswer(2) }
        Button(enabled = state.selectedIndex != null, onClick = {
            vm.next()
            if (vm.state.value.finished) {
                vm.commitResultsAndUpdateMistakes()
                val total = vm.state.value.questions.size
                val correct = vm.state.value.correctCount
                navController.navigate("result/${mode.name}/$total/$correct") {
                    popUpTo(Routes.MENU) { inclusive = false }
                }
            }
        }) {
            Text("Далее")
        }
        OutlinedButton(onClick = { navController.popBackStack() }) { Text("В меню") }
    }
}

@Composable
private fun OptionTrain(index: Int, text: String, selected: Boolean, correct: Boolean, showFeedback: Boolean, answered: Boolean, onClick: () -> Unit) {
    val isGreen = showFeedback && answered && correct
    val isRed = showFeedback && answered && !correct && selected
    val bg = when {
        isGreen -> Color(0x3328A745)
        isRed -> Color(0x33DC3545)
        else -> Color.Transparent
    }
    val content: @Composable () -> Unit = { Text(text, color = Color.Unspecified) }
    if (selected) {
        Button(onClick = onClick, modifier = Modifier.background(bg)) { content() }
    } else {
        OutlinedButton(onClick = onClick, modifier = Modifier.background(bg)) { content() }
    }
}

@Composable
fun StatsScreen(navController: NavHostController) {
    val vm: QuizViewModel = activityViewModel()
    val state by vm.state.collectAsState() // not actually used; repository call is needed
    val appVm: QuizViewModel = vm
    val contextNav = navController
    androidx.compose.runtime.LaunchedEffect(Unit) {
        // no-op to keep composition
    }
    // Simple blocking UI: fetch stats on composition via helper composable
    StatsContent(appVm = appVm, navController = contextNav)
}

@Composable
private fun StatsContent(appVm: QuizViewModel, navController: NavHostController) {
    // This is a simple one-shot fetch; a ViewModel for stats would be nicer, but reuse repo
    val stats = androidx.compose.runtime.produceState(initialValue = com.example.testprep.data.Stats(0,0,0,0,0,0), appVm) {
        value = appVm.run {
            com.example.testprep.data.QuestionRepository(getApplication()).getStats()
        }
    }.value

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Тестов пройдено: ${stats.testCount}")
        Text("Успешных тестов: ${stats.successfulTests}")
        Text("Тренировок пройдено: ${stats.trainingCount}")
        Text("Сессий 'Работа над ошибками': ${stats.mistakesCount}")
        Spacer(Modifier.height(8.dp))
        Text("Лучший результат (тренировка): ${stats.bestTrainingCorrect}")
        Text("Лучший результат (тест): ${stats.bestTestCorrect}")
        Spacer(Modifier.height(12.dp))
        Button(onClick = { navController.popBackStack() }) { Text("В меню") }
    }
}

@Composable
fun ResultScreen(navController: NavHostController, backStackEntry: NavBackStackEntry) {
    val mode = backStackEntry.arguments?.getString("mode") ?: "TEST"
    val total = backStackEntry.arguments?.getString("total")?.toIntOrNull() ?: 0
    val correct = backStackEntry.arguments?.getString("correct")?.toIntOrNull() ?: 0
    val vm: QuizViewModel = activityViewModel()
    val incorrect = vm.getIncorrectDetails()

    val success = mode == QuizMode.TEST.name && total > 0 && (correct * 100 / total) >= 76

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Режим: $mode")
        Text("Результат: $correct из $total")
        if (mode == QuizMode.TEST.name) {
            Text(if (success) "Тест пройден (>=76%)" else "Тест не пройден")
        }
        Spacer(Modifier.height(8.dp))
        Text("Ошибки:")
        if (incorrect.isEmpty()) {
            Text("Ошибок нет")
        } else {
            incorrect.forEach { (q, selected, correctIndex) ->
                Text("- ${q.questionText}")
                val answer = listOf(q.option1, q.option2, q.option3)[correctIndex]
                Text("  Правильный ответ: $answer")
            }
        }
        Spacer(Modifier.height(12.dp))
        Button(onClick = { navController.navigate(Routes.MENU) { popUpTo(Routes.MENU) { inclusive = true } } }) { Text("В меню") }
    }
}
