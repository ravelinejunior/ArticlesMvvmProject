package br.com.example.articlesmvvmproject.domain.usecase

import br.com.example.articlesmvvmproject.data.model.News
import br.com.example.articlesmvvmproject.data.util.Resource
import br.com.example.articlesmvvmproject.domain.repository.NewsRepository

class GetNewsHeadlinesUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(country: String, page: Int): Resource<News> =
        newsRepository.getNewsHeadLines(country, page)
}