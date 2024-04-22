package com.dicoding.asclepius.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dicoding.asclepius.BuildConfig
import com.dicoding.asclepius.data.local.entity.ArticleEntity
import com.dicoding.asclepius.data.local.room.ArticleDao
import com.dicoding.asclepius.data.remote.response.ArticleResponse
import com.dicoding.asclepius.data.remote.retrofit.ApiService
import com.dicoding.asclepius.utils.AppExecutors
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ArticleRepositories private constructor(
    private val apiService: ApiService,
    private val articleDao: ArticleDao,
    private val appExecutors: AppExecutors
) {
    private val result = MediatorLiveData<Result<List<ArticleEntity>>>()

    fun getHeadlineArticle(): LiveData<Result<List<ArticleEntity>>> {
        result.value = Result.Loading
        val client = apiService.getArticle(BuildConfig.API_KEY)
        client.enqueue(object : Callback<ArticleResponse> {
            override fun onResponse(
                call: Call<ArticleResponse>,
                response: Response<ArticleResponse>
            ) {
                if (response.isSuccessful) {
                    val articles = response.body()?.articles
                    val articleList = ArrayList<ArticleEntity>()
                    appExecutors.diskIO.execute {
                        articles?.forEach { articles ->
                            val article = ArticleEntity(
                                articles?.title!!,
                                articles.publishedAt!!,
                                articles.urlToImage,
                                articles.url
                            )
                            articleList.add(article)
                        }
                        articleDao.deleteAll()
                        articleDao.insertArticle(articleList)
                    }
                }
            }

            override fun onFailure(call: Call<ArticleResponse>, t: Throwable) {
                result.value = Result.Error(t.message.toString())
            }
        })
        val localData = articleDao.getArticle()
        result.addSource(localData) { articleData: List<ArticleEntity> ->
            result.value = Result.Success(articleData)
        }
        return result
    }

    companion object {
        @Volatile
        private var instance: ArticleRepositories? = null

        fun getInstance(
            apiService: ApiService,
            articleDao: ArticleDao,
            appExecutors: AppExecutors
        ): ArticleRepositories =
            instance ?: synchronized(this) {
                instance ?: ArticleRepositories(apiService, articleDao, appExecutors)
            }.also { instance = it }
    }
}