package com.xlwe.news.di.data

import com.xlwe.news.data.database.NewsListDao
import com.xlwe.news.data.network.ApiRequests
import com.xlwe.news.data.repositories.NewsRepositoryImpl
import com.xlwe.news.domain.repositories.NewsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
class RepositoriesModule {

    @Provides
    fun provideNewsRepository(
        apiRequests: ApiRequests,
        newsListDao: NewsListDao
    ) : NewsRepository {
        return NewsRepositoryImpl(apiRequests, newsListDao)
    }

}