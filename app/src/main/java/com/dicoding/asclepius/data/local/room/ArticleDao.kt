package com.dicoding.asclepius.data.local.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dicoding.asclepius.data.local.entity.ArticleEntity

@Dao
interface ArticleDao {
    @Query("SELECT * FROM article ORDER BY publishedAt DESC")
    fun getArticle(): LiveData<List<ArticleEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertArticle(article: List<ArticleEntity>)

    @Update
    fun updateArticle(article: ArticleEntity)

    @Query("DELETE FROM article")
    fun deleteAll()
}