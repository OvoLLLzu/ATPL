package com.example.testprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.example.testprep.data.entity.MistakeEntity

@Dao
interface MistakeDao {
    @Query("SELECT questionId FROM mistakes")
    suspend fun getAllQuestionIds(): List<Long>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun add(mistake: MistakeEntity)

    @Query("DELETE FROM mistakes WHERE questionId = :questionId")
    suspend fun remove(questionId: Long)

    @Query("DELETE FROM mistakes")
    suspend fun clear()

    @Transaction
    suspend fun addAll(questionIds: List<Long>) {
        for (id in questionIds) {
            add(MistakeEntity(questionId = id))
        }
    }
}
