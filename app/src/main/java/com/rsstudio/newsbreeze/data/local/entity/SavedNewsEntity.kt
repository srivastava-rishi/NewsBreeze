package com.rsstudio.newsbreeze.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savedNews" , primaryKeys = ["id"])
data class SavedNewsEntity(
    val id: Int,
    val title: String,
    val urlToImage: String? = null,
    val publishedAt: String,
    var author: String? = null
)
