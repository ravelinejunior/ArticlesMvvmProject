package br.com.example.articlesmvvmproject.domain.repository

import br.com.example.articlesmvvmproject.data.model.Article
import br.com.example.articlesmvvmproject.data.model.News
import br.com.example.articlesmvvmproject.data.util.Resource
import kotlinx.coroutines.flow.Flow


interface NewsRepository {
    suspend fun getNewsHeadLines(country: String, page: Int): Resource<News>
    suspend fun getSearchedNews(searchQuery: String): Resource<News>
    suspend fun saveNews(article: Article)
    suspend fun deleteNews(article: Article)
    fun getSavedNews(): Flow<List<Article>> // flow fica observando mudanças no estado da lista
}