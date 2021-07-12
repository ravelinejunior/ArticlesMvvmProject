package br.com.example.articlesmvvmproject.data.api


import br.com.example.articlesmvvmproject.BuildConfig
import br.com.example.articlesmvvmproject.data.model.News
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApiService {
    @GET("/top-headlines")
    suspend fun getTopHeadlines(
        @Query("country")
        country:String,
        @Query("page")
        page:Int,
        @Query("apiKey")
        apiKey:String = BuildConfig.API_KEY
    ):Response<News>
}