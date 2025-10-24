package com.example.testprep.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.testprep.data.entity.SessionEntity

@Dao
interface SessionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(session: SessionEntity)

    @Query("SELECT COUNT(*) FROM sessions WHERE mode = :mode")
    suspend fun countByMode(mode: String): Int

    @Query("SELECT COUNT(*) FROM sessions WHERE mode = 'TEST' AND (correct * 100) / total >= 76")
    suspend fun countSuccessfulTests(): Int

    @Query("SELECT MAX(correct) FROM sessions WHERE mode = 'TRAINING'")
    suspend fun bestTrainingCorrect(): Int?

    @Query("SELECT MAX(correct) FROM sessions WHERE mode = 'TEST'")
    suspend fun bestTestCorrect(): Int?
}
