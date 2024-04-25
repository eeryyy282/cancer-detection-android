package com.dicoding.asclepius.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.AnalyzeResultRepository
import com.dicoding.asclepius.di.Injection

class ResultViewModelFactory private constructor(
    private val analyzeResultRepository: AnalyzeResultRepository
) : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ResultViewModel::class.java)) {
            return ResultViewModel(analyzeResultRepository) as T
        }
        throw IllegalArgumentException("Viewmodel class tidak ditemukan")
    }

    companion object {
        @Volatile
        private var instance: ResultViewModelFactory? = null

        fun getInstance(context: Context): ResultViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: ResultViewModelFactory(
                    Injection.analyzeResultRepository(context)
                )
            }.also { instance = it }
    }
}