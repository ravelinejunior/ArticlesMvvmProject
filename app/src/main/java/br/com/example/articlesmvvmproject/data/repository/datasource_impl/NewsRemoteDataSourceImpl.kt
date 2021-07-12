package br.com.example.articlesmvvmproject.data.repository.datasource_impl

import br.com.example.articlesmvvmproject.data.api.NewsApiService
import br.com.example.articlesmvvmproject.data.model.News
import br.com.example.articlesmvvmproject.data.repository.datasource.NewsRemoteDataSource
import retrofit2.Response

class NewsRemoteDataSourceImpl(
    private val newsApiService: NewsApiService,

) : NewsRemoteDataSource {
    override suspend fun getTopHeadlines(country: String, page: Int): Response<News> {
        return newsApiService.getTopHeadlines(country,page)
    }
}