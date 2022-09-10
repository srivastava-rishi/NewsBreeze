package com.rsstudio.newsbreeze.data.network.repository

import com.rsstudio.newsbreeze.data.network.apis.NewsApiInterface
import com.rsstudio.newsbreeze.data.network.model.News
import retrofit2.Response
import javax.inject.Inject

class MainRepository
@Inject
constructor(private val api: NewsApiInterface) {

    suspend fun getNewsData(): Response<News> {
        return api.getNewsData()
    }

}