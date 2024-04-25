package com.dicoding.asclepius.view

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.asclepius.data.local.entity.AnalyzeResultEntity
import com.dicoding.asclepius.data.repository.AnalyzeResultRepository

class ResultViewModel(
    private val analyzeResultRepository: AnalyzeResultRepository
) : ViewModel() {

    private val _snackBarText = MutableLiveData<String>()
    val snackBarText: LiveData<String> = _snackBarText

    fun saveDetailUser(imageAnalyze: Uri, analyzeTime: String, analyzeResult: String) {
        val imageToString = imageAnalyze.toString()

        analyzeResultRepository.getAnalyzeResultByUri(imageToString) { result ->
            if (result == null) {
                val analyzeResultEntity = AnalyzeResultEntity(
                    imageUri = imageAnalyze,
                    analyzeTime = analyzeTime,
                    analyzeResult = analyzeResult
                )
                analyzeResultRepository.saveAnalyzeResult(analyzeResultEntity)
                _snackBarText.value = "Hasil analisis berhasil disimpan!"
            } else {
                _snackBarText.value = "Haisl analisis sudah pernah disimpan!"
            }
        }
    }


}

