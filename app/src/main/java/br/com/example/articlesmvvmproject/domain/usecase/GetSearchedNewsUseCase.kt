package br.com.example.articlesmvvmproject.domain.usecase

import br.com.example.articlesmvvmproject.data.model.News
import br.com.example.articlesmvvmproject.data.util.Resource
import br.com.example.articlesmvvmproject.domain.repository.NewsRepository

class GetSearchedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(searchString: String): Resource<News> =
        newsRepository.getSearchedNews(searchString)
}