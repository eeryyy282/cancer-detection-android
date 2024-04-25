package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.local.room.AnalyzeResultDatabase
import com.dicoding.asclepius.data.repository.ArticleRepository
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import com.dicoding.asclepius.data.repository.AnalyzeResultRepository
import com.dicoding.asclepius.utils.AppExecutors

object Injection {
    fun articleRepository(): ArticleRepository {
        val apiService = ApiConfig.getApiService()
        return ArticleRepository.getInstance(apiService)
    }

    fun analyzeResultRepository(context: Context): AnalyzeResultRepository {
        val database = AnalyzeResultDatabase.getInstance(context)
        val analyzeResultDao = database.analyzeResultDao()
        val appExecutors = AppExecutors()
        return AnalyzeResultRepository.getInstance(analyzeResultDao, appExecutors)
    }
}