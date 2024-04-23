package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.ArticleRepository
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import com.dicoding.asclepius.utils.AppExecutors

object Injection {
    fun articleRepository(): ArticleRepository {
        val apiService = ApiConfig.getApiService()
        return ArticleRepository.getInstance(apiService)
    }
}