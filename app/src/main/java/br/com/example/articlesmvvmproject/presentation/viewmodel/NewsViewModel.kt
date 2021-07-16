package br.com.example.articlesmvvmproject.presentation.viewmodel

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import br.com.example.articlesmvvmproject.data.model.News
import br.com.example.articlesmvvmproject.data.util.Resource
import br.com.example.articlesmvvmproject.domain.usecase.GetNewsHeadlinesUseCase
import br.com.example.articlesmvvmproject.domain.usecase.GetSearchedNewsUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NewsViewModel(
    private val getNewsHeadlinesUseCase: GetNewsHeadlinesUseCase,
    private val app: Application,
    private val getSearchedNewsUseCase: GetSearchedNewsUseCase
) : AndroidViewModel(app) {
    val newsHeadLines: MutableLiveData<Resource<News>> = MutableLiveData()
    val searchedNews: MutableLiveData<Resource<News>> = MutableLiveData()

    fun getNewsHeadlines(country: String, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            newsHeadLines.postValue(Resource.Loading())
            try {
                if (isNetworkAvailable(app)) {
                    val apiResult = getNewsHeadlinesUseCase.execute(country, page)
                    newsHeadLines.postValue(apiResult)
                } else {
                    newsHeadLines.postValue(Resource.Error("Internet is not Available."))
                }
            } catch (e: Exception) {
                newsHeadLines.postValue(Resource.Error(e.message))
            }
        }

    //search Headlines
    fun getSearchNews(searchQuery: String, country: String, page: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            //inicializar com modo loading
            searchedNews.postValue(Resource.Loading())

            //verificar se existe conexao com a internet
            try {
                if (isNetworkAvailable(app.applicationContext)) {
                    val response = getSearchedNewsUseCase.execute(
                        searchQuery, country, page
                    )

                    searchedNews.postValue(response)
                } else {
                    searchedNews.postValue(Resource.Error("No internet connection"))
                }
            } catch (e: Exception) {
                searchedNews.postValue(Resource.Error(e.message.toString()))
            }

        }

    private fun isNetworkAvailable(context: Context?): Boolean {
        if (context == null) return false

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)

            if (capabilities != null) {
                when {
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                        return true
                    }
                    capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                        return true
                    }

                }
            }
        } else {
            val activeNetworkInfo = connectivityManager.activeNetworkInfo
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected) {
                return true
            }
        }

        return false
    }

}
