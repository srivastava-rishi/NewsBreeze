package com.rsstudio.newsbreeze.data.network.apis

import com.rsstudio.newsbreeze.data.network.model.News
import retrofit2.Response
import retrofit2.http.GET

interface NewsApiInterface {

    @GET("top-headlines?country=us&apiKey=")
    suspend fun getNewsData(): Response<News>
}