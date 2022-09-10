package com.rsstudio.newsbreeze.data.network.model

data class News(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleData>
    )