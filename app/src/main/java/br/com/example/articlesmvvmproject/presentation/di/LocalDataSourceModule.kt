package br.com.example.articlesmvvmproject.presentation.di

import br.com.example.articlesmvvmproject.data.db.dao.ArticleDao
import br.com.example.articlesmvvmproject.data.repository.datasource.NewsLocalDataSource
import br.com.example.articlesmvvmproject.data.repository.datasource_impl.NewsLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class LocalDataSourceModule {

    @Singleton
    @Provides
    fun provideLocalDataSource(articleDao: ArticleDao): NewsLocalDataSource {
        return NewsLocalDataSourceImpl(articleDao)
    }
}