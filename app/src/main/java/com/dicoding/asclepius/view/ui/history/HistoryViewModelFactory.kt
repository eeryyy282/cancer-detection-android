package com.dicoding.asclepius.view.ui.history

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.data.repository.AnalyzeResultRepository
import com.dicoding.asclepius.di.Injection
import java.lang.IllegalArgumentException

class HistoryViewModelFactory private constructor(private val analyzeResultRepository: AnalyzeResultRepository) :
    ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HistoryViewModel::class.java)) {
            return HistoryViewModel(analyzeResultRepository) as T
        }
        throw IllegalArgumentException("Viewmodel class tidak diketahui: " + modelClass.name)
    }

    companion object {
        @Volatile
        private var instance: HistoryViewModelFactory? = null

        fun getInstance(context: Context): HistoryViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: HistoryViewModelFactory(Injection.analyzeResultRepository(context))
            }.also { instance = it }
    }
}