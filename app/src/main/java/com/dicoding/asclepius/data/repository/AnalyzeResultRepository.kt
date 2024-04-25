package com.dicoding.asclepius.data.repository

import com.dicoding.asclepius.data.local.entity.AnalyzeResultEntity
import com.dicoding.asclepius.data.local.room.AnalyzeResultDao
import com.dicoding.asclepius.utils.AppExecutors

class AnalyzeResultRepository(
    private val analyzeResultDao: AnalyzeResultDao,
    private val appExecutors: AppExecutors
) {

    fun saveAnalyzeResult(analyzeResultEntity: AnalyzeResultEntity) {
        appExecutors.diskIO.execute {
            analyzeResultDao.saveAnalyzeResult(analyzeResultEntity)
        }
    }

    fun removeAnalyzeResult(analyzeResultEntity: AnalyzeResultEntity) {
        appExecutors.diskIO.execute {
            analyzeResultDao.deleteAnalyzeResult(analyzeResultEntity)
        }
    }

    companion object {
        private const val TAG = "AnalyzeResultRepository"

        @Volatile
        private var instance: AnalyzeResultRepository? = null

        fun getInstance(
            analyzeResultDao: AnalyzeResultDao,
            appExecutors: AppExecutors
        ): AnalyzeResultRepository =
            instance ?: synchronized(this) {
                instance ?: AnalyzeResultRepository(analyzeResultDao, appExecutors)
            }.also { instance = it }
    }
}