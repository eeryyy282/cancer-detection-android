package com.dicoding.asclepius.data.local.entity

import android.net.Uri
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class AnalyzeResultEntity(
    @PrimaryKey(autoGenerate = true)

    @ColumnInfo(name = "imageUri")
    var imageUri: Uri,

    @ColumnInfo(name = "analyzeTime")
    var analyzeTime: String,

    @ColumnInfo(name = "analyzeResult")
    var analyzeResult: String

)