package br.com.example.articlesmvvmproject.presentation.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.example.articlesmvvmproject.domain.usecase.DeleteSavedNewsUseCase
import br.com.example.articlesmvvmproject.domain.usecase.GetNewsHeadlinesUseCase
import br.com.example.articlesmvvmproject.domain.usecase.GetSearchedNewsUseCase
import br.com.example.articlesmvvmproject.domain.usecase.SaveNewsUseCase
import br.com.example.articlesmvvmproject.presentation.viewmodel.NewsViewModel

class NewsViewModelFactory(
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val app: Application,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase,
    private val saveNewsUseCase: SaveNewsUseCase,
    private val deleteSavedNewsUseCase: DeleteSavedNewsUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NewsViewModel(
            getNewsHeadlinesUseCase,
            app,
            getSearchedNewsUseCase,
            saveNewsUseCase,
            deleteSavedNewsUseCase
        ) as T
    }
}