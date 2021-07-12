package br.com.example.articlesmvvmproject.presentation.di

import br.com.example.articlesmvvmproject.data.repository.NewsRepositoryImpl
import br.com.example.articlesmvvmproject.data.repository.datasource.NewsRemoteDataSource
import br.com.example.articlesmvvmproject.domain.repository.NewsRepository
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
        newsRemoteDataSource: NewsRemoteDataSource
    ): NewsRepository {
        return NewsRepositoryImpl(newsRemoteDataSource)
    }
}