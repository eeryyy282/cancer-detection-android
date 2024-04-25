package com.dicoding.asclepius.data.repository

import android.util.Log
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.Result
import com.dicoding.asclepius.data.remote.response.ArticleResponse
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepository private constructor(
    private val apiService: ApiService
) {
    fun getArticle(callback: (Result<List<ArticlesItem?>>) -> Unit) {
        val client = apiService.getArticle(BuildConfig.API_KEY)
        client.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    if (articles.isNullOrEmpty()) {
                        callback(Result.Error("Maaf artikel tidak dapat dimuat"))
                    } else {
                        callback(Result.Success(articles))
                    }
                } else {
                    callback(Result.Error("Gagl mendapatkan artikel : ${response.message()}"))
                    Log.e(TAG, "onfailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                callback(Result.Error("Artikel tidak dapat dimuat: ${t.message}"))
                Log.e(TAG, "onfailure: ${t.message}")
            }
        })
    }

    companion object {
        private const val TAG = "ArticleRepository"

        @Volatile
        private var instance: ArticleRepository? = null

        fun getInstance(
            apiService: ApiService
        ): ArticleRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(apiService)
            }.also { instance = it }
    }
}