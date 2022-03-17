package com.xlwe.news.data.network

import com.xlwe.news.data.network.model.NewsDTO
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiRequests {
    @GET("everything")
    suspend fun getNews(
        @Query("q") company: String,
        @Query("apiKey") apiKey: String,
    ): NewsDTO
}