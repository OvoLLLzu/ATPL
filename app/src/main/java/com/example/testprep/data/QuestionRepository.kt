package com.example.testprep.data

import android.content.Context
import com.example.testprep.data.entity.QuestionEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class QuestionRepository(private val context: Context) {
    private val db = AppDatabase.getInstance(context)

    suspend fun importQuestions(questions: List<QuestionEntity>) = withContext(Dispatchers.IO) {
        db.questionDao().clear()
        db.questionDao().insertAll(questions)
    }

    suspend fun getAll(): List<QuestionEntity> = withContext(Dispatchers.IO) {
        db.questionDao().getAll()
    }

    suspend fun getRandom(limit: Int): List<QuestionEntity> = withContext(Dispatchers.IO) {
        db.questionDao().getRandom(limit)
    }

    suspend fun getByIds(ids: List<Long>): List<QuestionEntity> = withContext(Dispatchers.IO) {
        if (ids.isEmpty()) emptyList() else db.questionDao().getByIds(ids)
    }

    suspend fun hasQuestions(): Boolean = withContext(Dispatchers.IO) {
        db.questionDao().count() > 0
    }

    suspend fun addMistakes(questionIds: List<Long>) = withContext(Dispatchers.IO) {
        db.mistakeDao().addAll(questionIds)
    }

    suspend fun removeMistake(questionId: Long) = withContext(Dispatchers.IO) {
        db.mistakeDao().remove(questionId)
    }

    suspend fun getMistakeQuestionIds(): List<Long> = withContext(Dispatchers.IO) {
        db.mistakeDao().getAllQuestionIds()
    }

    suspend fun saveSession(mode: String, total: Int, correct: Int) = withContext(Dispatchers.IO) {
        db.sessionDao().insert(
            com.example.testprep.data.entity.SessionEntity(
                timestamp = System.currentTimeMillis(),
                mode = mode,
                total = total,
                correct = correct
            )
        )
    }

    suspend fun getStats(): Stats = withContext(Dispatchers.IO) {
        val testCount = db.sessionDao().countByMode("TEST")
        val trainingCount = db.sessionDao().countByMode("TRAINING")
        val mistakesCount = db.sessionDao().countByMode("MISTAKES")
        val successTests = db.sessionDao().countSuccessfulTests()
        val bestTraining = db.sessionDao().bestTrainingCorrect() ?: 0
        val bestTest = db.sessionDao().bestTestCorrect() ?: 0
        Stats(
            testCount = testCount,
            trainingCount = trainingCount,
            mistakesCount = mistakesCount,
            successfulTests = successTests,
            bestTrainingCorrect = bestTraining,
            bestTestCorrect = bestTest,
        )
    }
}

data class Stats(
    val testCount: Int,
    val trainingCount: Int,
    val mistakesCount: Int,
    val successfulTests: Int,
    val bestTrainingCorrect: Int,
    val bestTestCorrect: Int,
)
