package com.xlwe.news.domain.repositories

import com.xlwe.news.domain.NetworkResult
import com.xlwe.news.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getNews(): Flow<NetworkResult>
    fun getNewsFromDatabase(): Flow<NetworkResult>
    suspend fun insert(article: Article)
    suspend fun deleteNews(article: Article)

}