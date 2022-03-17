package com.xlwe.news.data.repositories

import com.xlwe.news.data.database.NewsListDao
import com.xlwe.news.data.mapper.NewsMapper
import com.xlwe.news.data.network.ApiRequests
import com.xlwe.news.data.network.model.ArticleDTO
import com.xlwe.news.data.network.model.NewsDTO
import com.xlwe.news.data.network.model.SourceDTO
import com.xlwe.news.domain.NetworkResult
import com.xlwe.news.domain.model.Article
import com.xlwe.news.domain.repositories.NewsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val apiRequests: ApiRequests,
    private val newsListDao: NewsListDao
) : NewsRepository {

    private val mapper = NewsMapper()

    private fun GENERATE(name: String): NewsDTO {
        val networkList = mutableListOf<ArticleDTO>()

        for (i in 0..19) {
            val article = ArticleDTO(
                author = "author $i",
                content = "author $i",
                description = "author $i",
                publishedAt = "author $i",
                source = SourceDTO(
                    id = "id",
                    name = "name"
                ),
                title = "author $i",
                url = "author $i",
                urlToImage = "author $i"
            )

            networkList.add(article)
        }

        return NewsDTO(
            status = "ok",
            totalResults = 1,
            articles = networkList
        )
    }

    override fun getNews(): Flow<NetworkResult> = flow {
        val newsNvidia = apiRequests.getNews("nvidia", "885f7b7d16ed4472a7944e421215fa82")
        val listEntity = mapper.mapNetworkModelToEntity(newsNvidia.articles).toMutableList()

        val newsAmd = apiRequests.getNews("amd", "885f7b7d16ed4472a7944e421215fa82")
            mapper.mapNetworkModelToEntity(newsAmd.articles).forEach {
                listEntity.add(it)
            }

        val newsApple = apiRequests.getNews("apple", "885f7b7d16ed4472a7944e421215fa82")
            mapper.mapNetworkModelToEntity(newsApple.articles).forEach {
                listEntity.add(it)
            }

        emit(NetworkResult.Success(listEntity))
    }.flowOn(Dispatchers.IO)

    override fun getNewsFromDatabase(): Flow<NetworkResult> = flow {
        newsListDao.getNews().collect {
            emit(NetworkResult.Success(mapper.mapDbModelToEntity(it)))
        }
    }

    override suspend fun insert(article: Article) {
        newsListDao.addNews(mapper.mapEntityModelToDb(article))
    }

    override suspend fun deleteNews(article: Article) {
        newsListDao.deleteNews(article.id)
    }

}