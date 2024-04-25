package com.dicoding.asclepius.data.repository

import androidx.lifecycle.LiveData
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

    fun showAnalyzeResult(): LiveData<List<AnalyzeResultEntity>> {
        return analyzeResultDao.showAnalyzeResult()
    }

    fun getAnalyzeResultByUri(imageUri: String, callback: (AnalyzeResultEntity?) -> Unit) {
        appExecutors.diskIO.execute {
            val result = analyzeResultDao.getAnalyzeResultByUri(imageUri)
            appExecutors.mainThread.execute {
                callback(result)
            }
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