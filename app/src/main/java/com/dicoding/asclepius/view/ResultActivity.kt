package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.dicoding.asclepius.R
import com.dicoding.asclepius.data.local.entity.AnalyzeResultEntity
import com.dicoding.asclepius.data.repository.AnalyzeResultRepository
import com.dicoding.asclepius.databinding.ActivityResultBinding
import com.dicoding.asclepius.helper.DateHelper
import com.google.android.material.snackbar.Snackbar

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    private lateinit var resultViewModel: ResultViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factoryResult: ResultViewModelFactory =
            ResultViewModelFactory.getInstance(application)
        resultViewModel =
            ViewModelProvider(this, factoryResult)[ResultViewModel::class.java]


        resultViewModel.snackBarText.observe(this) {
            Snackbar.make(window.decorView.rootView, it, Snackbar.LENGTH_SHORT).show()
        }

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


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
                resultViewModel.saveDetailUser(imageUri, analyzeDate, analyzeResult)
            } else {
                //("Tidak bisa menyimpan hasil analisis, gambar dan hasil tidak terdeteksi")
            }
        }

    }


    companion object {
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_RESULT = "extra_result"
    }
}
