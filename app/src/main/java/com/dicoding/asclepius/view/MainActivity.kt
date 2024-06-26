package com.dicoding.asclepius.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.dicoding.asclepius.databinding.ActivityMainBinding
import com.dicoding.asclepius.helper.ImageClassifierHelper
import com.yalantis.ucrop.UCrop
import org.tensorflow.lite.task.vision.classifier.Classifications
import java.io.File
import java.text.NumberFormat
import java.util.UUID

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var imageClasifierHelper: ImageClassifierHelper

    private var currentImageUri: Uri? = null
    private var resultAnalyze: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.galleryButton.setOnClickListener { startGallery() }
        binding.analyzeButton.setOnClickListener { analyzeImage() }

    }


    @Deprecated("Deprecated in Java")
    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            currentImageUri = UCrop.getOutput(data!!)
            showImage()

        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            Log.d("Fungsi crop error", "showImage: $cropError")
            showToast("Fungsi crop error")
        }
    }

    private fun startGallery() {
        launchGallery.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
    }

    private val launchGallery = registerForActivityResult(
        ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            launchUCrop(uri)
            showImage()
        } else {
            Log.d("Photo Picker", "Foto tidak ditemukan")
        }
    }

    private fun launchUCrop(uri: Uri) {
        val destinationFileName = "${UUID.randomUUID()}.jpg"
        val destinationUri = Uri.fromFile(File(filesDir, destinationFileName))

        val options = UCrop.Options()
        options.setCompressionQuality(100)

        UCrop.of(uri, destinationUri)
            .withOptions(options)
            .withAspectRatio(0f, 0f)
            .useSourceImageAspectRatio()
            .withMaxResultSize(3000, 3000)
            .start(this)
    }


    private fun showImage() {
        currentImageUri?.let {
            Log.d("URI Gambar", "showImage: $it")
            binding.previewImageView.setImageURI(it)
        }
    }

    private fun analyzeImage() {
        if (currentImageUri != null) {
            imageClasifierHelper = ImageClassifierHelper(
                context = this,
                classifierListener = object : ImageClassifierHelper.ClassifierListener {
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
                                    "${it.label}\n" + NumberFormat.getPercentInstance()
                                        .format(it.score).trim()
                                }
                            resultAnalyze = displayResult
                            moveToResult()
                        }
                    }

                }
            )
            currentImageUri?.let { imageClasifierHelper.classifyStaticImage(it) }
        } else {
            showToast("Pilih gambar terlebih dahulu")
        }
    }


    private fun moveToResult() {
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(EXTRA_IMAGE, currentImageUri)
        intent.putExtra(EXTRA_RESULT, resultAnalyze)
        startActivity(intent)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }

    companion object {
        const val EXTRA_IMAGE = "extra_image"
        const val EXTRA_RESULT = "extra_result"
    }
}