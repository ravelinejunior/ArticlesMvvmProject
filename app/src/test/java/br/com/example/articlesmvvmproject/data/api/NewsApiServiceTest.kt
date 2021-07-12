package br.com.example.articlesmvvmproject.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class NewsApiServiceTest {

    private lateinit var service: NewsApiService
    private lateinit var server: MockWebServer

    @Before
    fun setUp() {
        server = MockWebServer()
        service = Retrofit.Builder()
            .baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(NewsApiService::class.java)
    }

    private fun enqueueMockResponse(fileName: String) {
        val inputStream = javaClass.classLoader?.getResourceAsStream(fileName)
        val source = inputStream?.source()?.buffer()
        val mockResponse = MockResponse()

        mockResponse.setBody(source!!.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }

    @Test
    fun getTopHeadlines_sentRequest_receivedExpected() {
        runBlocking {
            enqueueMockResponse("newsResponse.json")
            val responseBody = service.getTopHeadlines("br", 1).body()
            val request = server.takeRequest()

            print(responseBody)

            assertThat(responseBody).isNotNull()
            assertThat(request.path).isEqualTo("/top-headlines?country=br&page=1&apiKey=a7694fae483746288f786e453a2ccad6")
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockResponse("newsResponse.json")
            val response = service.getTopHeadlines("br", 1).body()
            print("$response\n")

            val articleList = response?.articles
            print("Page size ${articleList!!.size}")

            assertThat(articleList.size).isEqualTo(20)
        }
    }

    @Test
    fun getTopHeadlines_receivedResponse_correctAuthorName() {
        runBlocking {
            enqueueMockResponse("newsResponse.json")
            val response = service.getTopHeadlines("br", 1).body()
            print("$response\n\n")

            val articleList = response?.articles
            val article = articleList?.get(0)
            print("Author name ${article!!.author}")

            assertThat(article.author).isEqualTo("UOL")
            assertThat(article.url).isEqualTo("ttps://www.uol.com.br/vivabem/noticias/redacao/2021/07/07/doria-antecipa-vacinacao-em-sp-e-flexibiliza-horarios-ate-23h.htm")


        }
    }


    @After
    fun tearDown() {
        server.shutdown()
    }
}