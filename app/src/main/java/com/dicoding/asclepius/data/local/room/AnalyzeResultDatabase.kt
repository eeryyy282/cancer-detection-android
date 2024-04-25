package com.dicoding.asclepius.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dicoding.asclepius.data.local.entity.AnalyzeResultEntity

@Database(entities = [AnalyzeResultEntity::class], version = 1)
@TypeConverters(UriConverter::class)
abstract class AnalyzeResultDatabase : RoomDatabase() {
    abstract fun analyzeResultDao(): AnalyzeResultDao

    companion object {
        @Volatile
        private var INSTANCE: AnalyzeResultDatabase? = null

        @JvmStatic
        fun getInstance(context: Context): AnalyzeResultDatabase {
            if (INSTANCE == null) {
                synchronized(AnalyzeResultDatabase::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        AnalyzeResultDatabase::class.java, "analyze_result_database"
                    )
                        .build()
                }
            }
            return INSTANCE as AnalyzeResultDatabase
        }
    }
}
