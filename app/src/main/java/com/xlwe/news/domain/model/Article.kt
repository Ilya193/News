package com.xlwe.news.domain.model

data class Article(
    val id: Int = 0,
    val author: String,
    val content: String,
    val title: String,
    val urlToImage: String,
    var type: Int = 1
)