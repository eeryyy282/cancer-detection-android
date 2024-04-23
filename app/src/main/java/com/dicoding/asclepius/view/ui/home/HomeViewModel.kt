package com.dicoding.asclepius.view.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.ArticleRepository
import com.dicoding.asclepius.data.remote.response.ArticlesItem
import com.dicoding.asclepius.data.Result

class HomeViewModel(
    private val articleRepository: ArticleRepository
) : ViewModel() {

    private val _articles = MutableLiveData<Result<List<ArticlesItem?>>>()

    val articles: LiveData<Result<List<ArticlesItem?>>>
        get() = _articles

    fun findArticles() {
        _articles.value = Result.Loading

        articleRepository.getArticle { result ->
            _articles.value = result
        }
    }
}