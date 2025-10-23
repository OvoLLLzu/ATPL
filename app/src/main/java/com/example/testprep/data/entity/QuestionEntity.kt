package com.example.testprep.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class QuestionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val questionText: String,
    val option1: String,
    val option2: String,
    val option3: String,
    val correctIndex: Int, // 0..2
)
