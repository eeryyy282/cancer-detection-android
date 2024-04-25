package com.dicoding.asclepius.di

import com.dicoding.asclepius.data.repository.ArticleRepository
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig

object Injection {
    fun articleRepository(): ArticleRepository {
        val apiService = ApiConfig.getApiService()
        return ArticleRepository.getInstance(apiService)
    }
}