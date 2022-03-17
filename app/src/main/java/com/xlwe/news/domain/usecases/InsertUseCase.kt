package com.xlwe.news.domain.usecases

import com.xlwe.news.domain.model.Article
import com.xlwe.news.domain.repositories.NewsRepository
import kotlin.math.atan

class InsertUseCase(
    private val newsRepository: NewsRepository
) {

    suspend fun insert(article: Article) {
        newsRepository.insert(article)
    }

}