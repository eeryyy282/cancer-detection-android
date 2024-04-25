package com.dicoding.asclepius.view.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.ArticleRepository
import com.dicoding.asclepius.di.Injection

class HomeViewModelFactory(
    private val articleRepository: ArticleRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(articleRepository) as T
        }
        throw IllegalArgumentException("Viewmodel class tidak diketahui: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: HomeViewModelFactory? = null
        fun getInstance(): HomeViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HomeViewModelFactory(
                    Injection.articleRepository()
                )
            }.also { instance = it }
    }
}