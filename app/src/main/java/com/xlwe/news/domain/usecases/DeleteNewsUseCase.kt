package com.xlwe.news.domain.usecases

import com.xlwe.news.domain.model.Article
import com.xlwe.news.domain.repositories.NewsRepository

class DeleteNewsUseCase(
    private val newsRepository: NewsRepository
) {

    suspend fun deleteNews(article: Article) {
        newsRepository.deleteNews(article)
    }

}