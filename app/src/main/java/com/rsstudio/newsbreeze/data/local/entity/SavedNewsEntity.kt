package com.rsstudio.newsbreeze.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "savedNews")
data class SavedNewsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int ,
    val title: String,
    val urlToImage: String,
    val publishedAt: String,
    val author: String,
)
