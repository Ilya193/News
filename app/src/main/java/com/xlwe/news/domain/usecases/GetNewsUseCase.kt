package com.xlwe.news.domain.usecases

import com.xlwe.news.domain.NetworkResult
import com.xlwe.news.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsUseCase(
    private val newsRepository: NewsRepository
) {

    fun getNews(): Flow<NetworkResult> {
        return newsRepository.getNews()
    }

}