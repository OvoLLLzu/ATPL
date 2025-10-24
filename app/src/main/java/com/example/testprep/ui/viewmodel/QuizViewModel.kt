package com.example.testprep.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.testprep.data.QuestionRepository
import com.example.testprep.data.entity.QuestionEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

enum class QuizMode { TEST, TRAINING, MISTAKES }

data class QuizState(
    val questions: List<QuestionEntity> = emptyList(),
    val currentIndex: Int = 0,
    val selectedIndex: Int? = null,
    val correctCount: Int = 0,
    val finished: Boolean = false,
    val selections: List<Int?> = emptyList(),
)

class QuizViewModel(app: Application) : AndroidViewModel(app) {
    private val repository = QuestionRepository(app)
    private val _state = MutableStateFlow(QuizState())
    val state = _state.asStateFlow()

    private var mode: QuizMode = QuizMode.TEST

    fun start(mode: QuizMode) {
        this.mode = mode
        viewModelScope.launch {
            val qList = when (mode) {
                QuizMode.TEST -> repository.getRandom(67)
                QuizMode.TRAINING -> repository.getAll().shuffled()
                QuizMode.MISTAKES -> repository.getByIds(repository.getMistakeQuestionIds()).shuffled()
            }
            val newState = QuizState(
                questions = qList,
                selections = List(qList.size) { null }
            )
            _state.value = newState
        }
    }

    fun selectAnswer(index: Int) {
        val s = _state.value
        val updated = s.selections.toMutableList()
        if (s.currentIndex in updated.indices) {
            updated[s.currentIndex] = index
        }
        val newState = s.copy(selectedIndex = index, selections = updated)
        _state.value = newState
    }

    fun next() {
        val s = _state.value
        val isCorrect = s.selectedIndex == s.questions[s.currentIndex].correctIndex
        val newCorrect = s.correctCount + if (isCorrect) 1 else 0
        val nextIndex = s.currentIndex + 1
        val finished = nextIndex >= s.questions.size
        val newState = s.copy(
            currentIndex = if (finished) s.currentIndex else nextIndex,
            selectedIndex = null,
            correctCount = newCorrect,
            finished = finished,
        )
        _state.value = newState
    }

    fun commitResultsAndUpdateMistakes() {
        viewModelScope.launch {
            val s = _state.value
            val incorrectIds = s.questions.mapIndexedNotNull { idx, q ->
                val sel = s.selections.getOrNull(idx)
                if (sel == null || sel != q.correctIndex) q.id else null
            }
            val correctIds = s.questions.mapIndexedNotNull { idx, q ->
                val sel = s.selections.getOrNull(idx)
                if (sel != null && sel == q.correctIndex) q.id else null
            }

            when (mode) {
                QuizMode.TEST, QuizMode.TRAINING -> {
                    repository.addMistakes(incorrectIds)
                }
                QuizMode.MISTAKES -> {
                    // Remove correct answers from mistakes, keep incorrect
                    correctIds.forEach { repository.removeMistake(it) }
                }
            }

            repository.saveSession(mode.name, s.questions.size, s.correctCount)
        }
    }

    fun getIncorrectDetails(): List<Triple<QuestionEntity, Int?, Int>> {
        val s = _state.value
        return s.questions.mapIndexedNotNull { idx, q ->
            val sel = s.selections.getOrNull(idx)
            if (sel == null || sel != q.correctIndex) Triple(q, sel, q.correctIndex) else null
        }
    }
}
