package br.com.example.articlesmvvmproject.domain.usecase

import br.com.example.articlesmvvmproject.data.model.Article
import br.com.example.articlesmvvmproject.domain.repository.NewsRepository

class SaveNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.saveNews(article)
}