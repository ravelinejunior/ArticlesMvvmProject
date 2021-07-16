package br.com.example.articlesmvvmproject.data.repository

import br.com.example.articlesmvvmproject.data.model.Article
import br.com.example.articlesmvvmproject.data.model.News
import br.com.example.articlesmvvmproject.data.repository.datasource.NewsLocalDataSource
import br.com.example.articlesmvvmproject.data.repository.datasource.NewsRemoteDataSource
import br.com.example.articlesmvvmproject.data.util.Resource
import br.com.example.articlesmvvmproject.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class NewsRepositoryImpl(
    private val newsRemoteDataSource: NewsRemoteDataSource,
    private val newsLocalDataSource: NewsLocalDataSource
) : NewsRepository {

    override suspend fun getNewsHeadLines(country: String, page: Int): Resource<News> {
        return responseToResource(newsRemoteDataSource.getTopHeadlines(country, page))
    }

    override suspend fun getSearchedNews(
        searchQuery: String,
        country: String,
        page: Int
    ): Resource<News> {
        return responseToResource(
            newsRemoteDataSource.getTopHeadlinesSearched(
                searchQuery,
                country,
                page
            )
        )
    }

    override suspend fun saveNews(article: Article) {
        newsLocalDataSource.saveArticleDb(article)
    }

    override suspend fun deleteNews(article: Article) {
        newsLocalDataSource.deleteArticleDb(article)
    }

    override fun getSavedNews(): Flow<List<Article>> {
        TODO("Not yet implemented")
    }

    private fun responseToResource(response: Response<News>): Resource<News> {
        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(result)
            }
        }
        return Resource.Error(response.message())
    }
}