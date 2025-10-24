package com.example.testprep.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "sessions")
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val timestamp: Long,
    val mode: String, // TEST, TRAINING, MISTAKES
    val total: Int,
    val correct: Int,
)
