package br.com.example.articlesmvvmproject.domain.usecase

import br.com.example.articlesmvvmproject.data.model.Article
import br.com.example.articlesmvvmproject.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetSavedNewsUseCase(private val newsRepository: NewsRepository) {
     fun execute(): Flow<List<Article>> = newsRepository.getSavedNews()
}