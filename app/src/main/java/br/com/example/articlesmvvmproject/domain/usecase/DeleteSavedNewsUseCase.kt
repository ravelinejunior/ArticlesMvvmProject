package br.com.example.articlesmvvmproject.domain.usecase

import br.com.example.articlesmvvmproject.data.model.Article
import br.com.example.articlesmvvmproject.domain.repository.NewsRepository

class DeleteSavedNewsUseCase(private val newsRepository: NewsRepository) {
    suspend fun execute(article: Article) = newsRepository.deleteNews(article)
}