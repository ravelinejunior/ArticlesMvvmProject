package br.com.example.articlesmvvmproject.presentation.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.example.articlesmvvmproject.domain.usecase.*
import br.com.example.articlesmvvmproject.presentation.viewmodel.NewsViewModel

class NewsViewModelFactory(
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val app: Application,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase,
    private val getSavedNewsUseCase: GetSavedNewsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(
            getNewsHeadlinesUseCase,
            app,
            getSearchedNewsUseCase,
            saveNewsUseCase,
            deleteSavedNewsUseCase,
            getSavedNewsUseCase
        ) as T
    }
}