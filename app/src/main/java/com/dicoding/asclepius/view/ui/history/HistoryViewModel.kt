package com.dicoding.asclepius.view.ui.history

import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.entity.AnalyzeResultEntity
import com.dicoding.asclepius.data.repository.AnalyzeResultRepository

class HistoryViewModel(
    private val analyzeResultRepository: AnalyzeResultRepository
) : ViewModel() {
    fun getAnalyzeResult() = analyzeResultRepository.showAnalyzeResult()

    fun removeAnalyzeResult(analyzeResultEntity: AnalyzeResultEntity) {
        analyzeResultRepository.removeAnalyzeResult(analyzeResultEntity)
    }

}