package com.xlwe.news.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "news_items")
data class NewsItemDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val author: String,
    val content: String,
    val title: String,
    val urlToImage: String,
    val type: Int
)
