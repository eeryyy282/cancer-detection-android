package com.dicoding.asclepius.data.local.room

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.dicoding.asclepius.data.local.entity.AnalyzeResultEntity
import retrofit2.http.Query

interface AnalyzeResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAnalyzeResult(analyzeResultEntity: AnalyzeResultEntity)

    @Delete
    fun deleteAnalyzeResult(analyzeResultEntity: AnalyzeResultEntity)
}