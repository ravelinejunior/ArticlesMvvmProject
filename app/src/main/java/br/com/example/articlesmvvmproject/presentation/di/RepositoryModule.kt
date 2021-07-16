package br.com.example.articlesmvvmproject.presentation.di

import br.com.example.articlesmvvmproject.data.repository.NewsRepositoryImpl
import br.com.example.articlesmvvmproject.data.repository.datasource.NewsLocalDataSource
import br.com.example.articlesmvvmproject.data.repository.datasource.NewsRemoteDataSource
import br.com.example.articlesmvvmproject.domain.repository.NewsRepository
import br.com.example.articlesmvvmproject.domain.usecase.DeleteSavedNewsUseCase
import br.com.example.articlesmvvmproject.domain.usecase.SaveNewsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideNewsRepository(
        newsRemoteDataSource: NewsRemoteDataSource,
        newsLocalDataSource: NewsLocalDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource,newsLocalDataSource)
    }
}