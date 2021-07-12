package br.com.example.articlesmvvmproject.presentation.di

import br.com.example.articlesmvvmproject.data.api.NewsApiService
import br.com.example.articlesmvvmproject.data.repository.datasource.NewsRemoteDataSource
import br.com.example.articlesmvvmproject.data.repository.datasource_impl.NewsRemoteDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RemoteDataSource {

    @Singleton
    @Provides
    fun provideNewsRemoteDataSource(newsApiService: NewsApiService):NewsRemoteDataSource{
        return NewsRemoteDataSourceImpl(newsApiService)
    }

}