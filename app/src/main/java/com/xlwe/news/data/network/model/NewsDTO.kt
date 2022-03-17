package com.xlwe.news.data.network.model

data class NewsDTO(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDTO>
)