package com.example.testprep.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.testprep.data.dao.MistakeDao
import com.example.testprep.data.dao.QuestionDao
import com.example.testprep.data.dao.SessionDao
import com.example.testprep.data.entity.MistakeEntity
import com.example.testprep.data.entity.QuestionEntity
import com.example.testprep.data.entity.SessionEntity

@Database(
    entities = [QuestionEntity::class, MistakeEntity::class, SessionEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun questionDao(): QuestionDao
    abstract fun mistakeDao(): MistakeDao
    abstract fun sessionDao(): SessionDao

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "test_prep_db"
            ).fallbackToDestructiveMigration().build().also { INSTANCE = it }
        }
    }
}
