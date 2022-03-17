package com.xlwe.news.domain.usecases

import com.xlwe.news.domain.NetworkResult
import com.xlwe.news.domain.repositories.NewsRepository
import kotlinx.coroutines.flow.Flow

class GetNewsFromDatabaseUseCase(
    private val newsRepository: NewsRepository
) {

    fun getNewsFromDatabase(): Flow<NetworkResult> {
        return newsRepository.getNewsFromDatabase()
    }

}