package br.com.example.articlesmvvmproject.data.repository.datasource

import br.com.example.articlesmvvmproject.data.model.News
import retrofit2.Response

interface NewsRemoteDataSource {
    suspend fun getTopHeadlines(country: String, page: Int): Response<News>
    suspend fun getTopHeadlinesSearched(query:String,country: String, page: Int): Response<News>
}