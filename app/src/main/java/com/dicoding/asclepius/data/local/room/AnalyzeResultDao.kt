package com.dicoding.asclepius.data.local.room

import android.net.Uri
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.TypeConverter
import com.dicoding.asclepius.data.local.entity.AnalyzeResultEntity

@Dao
interface AnalyzeResultDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun saveAnalyzeResult(analyzeResultEntity: AnalyzeResultEntity): Long

    @Query("SELECT * FROM AnalyzeResultEntity WHERE imageUri = :imageUri LIMIT 1")
    fun getAnalyzeResultByUri(imageUri: String): AnalyzeResultEntity?

    @Delete
    fun deleteAnalyzeResult(analyzeResultEntity: AnalyzeResultEntity)
}

class UriConverter {
    @TypeConverter
    fun fromUri(uri: Uri?): String? {
        return uri?.toString()
    }

    @TypeConverter
    fun toUri(uriString: String?): Uri? {
        return uriString?.let { Uri.parse(it) }
    }

}