package com.xlwe.news.data.mapper

import com.xlwe.news.data.database.NewsItemDbModel
import com.xlwe.news.data.network.model.ArticleDTO
import com.xlwe.news.domain.model.Article

class NewsMapper {

    fun mapNetworkModelToEntity(articleDTO: List<ArticleDTO>?): List<Article> {
        val entityNews = mutableListOf<Article>()

        articleDTO?.forEach {
            val news = Article(
                author =  it.author ?: "null",
                content = it.content,
                title = it.title,
                urlToImage = it.urlToImage
            )

            entityNews.add(news)
        }

        return entityNews
    }

    fun mapEntityModelToDb(article: Article): NewsItemDbModel {
        return NewsItemDbModel(
            id = 0,
            author = article.author,
            content = article.content,
            title = article.title,
            urlToImage =  article.urlToImage,
            type = 2
        )
    }

    fun mapDbModelToEntity(article: List<NewsItemDbModel>): List<Article> {
        val entityNews = mutableListOf<Article>()

        article.forEach {
            val news = Article(
                id = it.id,
                author = it.author,
                content = it.content,
                title = it.title,
                urlToImage =  it.urlToImage,
                type = it.type
            )
            entityNews.add(news)
        }

        return entityNews
    }

}