package br.com.example.articlesmvvmproject.presentation.di

import android.app.Application
import br.com.example.articlesmvvmproject.domain.usecase.*
import br.com.example.articlesmvvmproject.presentation.factory.NewsViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FactoryModule {

    @Singleton
    @Provides
    fun provideNewsViewModelFactory(
        application: Application,
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
        getSearchedNewsUseCase: GetSearchedNewsUseCase,
        saveNewsUseCase: SaveNewsUseCase,
        deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
        getSavedNewsUseCase: GetSavedNewsUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(
            getNewsHeadlinesUseCase,
            application,
            getSearchedNewsUseCase,
            saveNewsUseCase,
            deleteSavedNewsUseCase,
            getSavedNewsUseCase
        )
    }


}