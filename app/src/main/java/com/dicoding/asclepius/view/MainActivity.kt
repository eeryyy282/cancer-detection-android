package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.text.NumberFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageClasifierHelper: ImageClassifierHelper

    private var currentImageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener { analyzeImage() }

    }

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            showImage()
        } else {
            Log.d("Photo Picker", "Foto tidak ditemukan")
        }
    }

    private fun showImage() {
        currentImageUri?.let {
            Log.d("URI Gambar", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        imageClasifierHelper = ImageClassifierHelper(
            context = this,
            clasifierListener = object : ImageClassifierHelper.ClassifierLisrener {
                override fun onError(error: String) {
                    showToast(error)
                }

                override fun onResults(result: List<Classifications>?) {
                    result?.let { it ->
                        println(it)
                        val sortedCategory =
                            it[0].categories.sortedByDescending { it?.score }
                        val displayResult =
                            sortedCategory.joinToString("\n") {
                                "${it.label} " + NumberFormat.getPercentInstance()
                                    .format(it.score).trim()
                            }
                    }
                }

            }
        )
        currentImageUri?.let { imageClasifierHelper.classifyStaticImage(it) }
    }


    private fun moveToResult() {
        val intent = Intent(this, ResultActivity::class.java)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}