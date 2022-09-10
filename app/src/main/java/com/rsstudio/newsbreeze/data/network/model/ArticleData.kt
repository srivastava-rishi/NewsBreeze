package com.rsstudio.newsbreeze.data.network.model

import java.io.Serializable

data class ArticleData(
    val source: SourceData,
    val author: String?,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String,
    val publishedAt: String,
    val content: String
    ): Serializable