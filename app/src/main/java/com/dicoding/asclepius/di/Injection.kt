package com.dicoding.asclepius.di

import android.content.Context
import com.dicoding.asclepius.data.ArticleRepositories
import com.dicoding.asclepius.data.local.room.ArticleDatabase
import com.dicoding.asclepius.data.remote.retrofit.ApiConfig
import com.dicoding.asclepius.utils.AppExecutors

object Injection {
    fun provideRepository(context: Context): ArticleRepositories {
        val apiService = ApiConfig.getApiService()
        val database = ArticleDatabase.getInstance(context)
        val dao = database.articleDao()
        val appExecutors = AppExecutors()
        return ArticleRepositories.getInstance(apiService, dao, appExecutors)
    }
}