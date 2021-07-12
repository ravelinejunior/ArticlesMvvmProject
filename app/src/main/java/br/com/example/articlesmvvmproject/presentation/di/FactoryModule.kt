package br.com.example.articlesmvvmproject.presentation.di

import android.app.Application
import br.com.example.articlesmvvmproject.domain.usecase.GetNewsHeadlinesUseCase
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
        getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase
    ): NewsViewModelFactory {
        return NewsViewModelFactory(getNewsHeadlinesUseCase, application)
    }


}