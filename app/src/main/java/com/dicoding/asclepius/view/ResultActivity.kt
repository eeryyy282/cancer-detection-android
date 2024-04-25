package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.AnalyzeResultEntity
import com.dicoding.asclepius.data.repository.AnalyzeResultRepository
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.DateHelper

class ResultActivity(
    private val analyzeResultRepository: AnalyzeResultRepository
) : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val imageUri: Uri? = intent.getParcelableExtra(EXTRA_IMAGE)
        val analyzeResult = intent.getStringExtra(EXTRA_RESULT)
        val analyzeDate = DateHelper.getCurrentDate()

        binding.resultImage.setImageURI(imageUri)
        binding.resultText.text = analyzeResult

        binding.btnBack.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            }
            startActivity(intent)
        }

        binding.btnSave.setOnClickListener {
            if (imageUri != null && analyzeResult != null) {
                val analyzeResultEntity = AnalyzeResultEntity(imageUri, analyzeDate, analyzeResult)
                analyzeResultRepository.saveAnalyzeResult(analyzeResultEntity)
            } else {
                showToast("TIdak bisa menyimpan hasil analisi, gambar dan hasil tidak terdeteksi")
            }
        }

    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_RESULT = "extra_result"
    }


}