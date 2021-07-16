package br.com.example.articlesmvvmproject.data.db.dao

import androidx.room.*
import br.com.example.articlesmvvmproject.data.model.Article
import kotlinx.coroutines.flow.Flow

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: Article)

    @Delete
    suspend fun deleteArticle(article: Article)

    @Query("SELECT * FROM ARTICLES_TB")
    fun getAllSavedArticles():Flow<List<Article>>



}