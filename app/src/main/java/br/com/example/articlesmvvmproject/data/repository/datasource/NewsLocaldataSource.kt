package br.com.example.articlesmvvmproject.data.repository.datasource

import br.com.example.articlesmvvmproject.data.model.Article

interface NewsLocaldataSource {
    suspend fun saveArticleDb(article: Article)
    suspend fun deleteArticleDb(article: Article)
}