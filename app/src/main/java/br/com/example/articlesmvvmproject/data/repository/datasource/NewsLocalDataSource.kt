package br.com.example.articlesmvvmproject.data.repository.datasource

import br.com.example.articlesmvvmproject.data.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsLocalDataSource {
    suspend fun saveArticleDb(article: Article)
    suspend fun deleteArticleDb(article: Article)
    fun getAllSavedArticles(): Flow<List<Article>>
}