package br.com.example.articlesmvvmproject.data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(articleDao: ArticleDao)

    @Delete
    suspend fun deleteArticle(articleDao: ArticleDao)


}