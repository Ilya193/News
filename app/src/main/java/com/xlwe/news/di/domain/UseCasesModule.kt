package com.xlwe.news.di.domain

import com.xlwe.news.domain.repositories.NewsRepository
import com.xlwe.news.domain.usecases.DeleteNewsUseCase
import com.xlwe.news.domain.usecases.GetNewsFromDatabaseUseCase
import com.xlwe.news.domain.usecases.GetNewsUseCase
import com.xlwe.news.domain.usecases.InsertUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    fun provideGetNewsUseCase(newsRepository: NewsRepository): GetNewsUseCase {
        return GetNewsUseCase(newsRepository)
    }

    @Provides
    fun provideInsertUseCase(newsRepository: NewsRepository): InsertUseCase {
        return InsertUseCase(newsRepository)
    }

    @Provides
    fun provideGetNewsFromDatabaseUseCase(newsRepository: NewsRepository): GetNewsFromDatabaseUseCase {
        return GetNewsFromDatabaseUseCase(newsRepository)
    }

    @Provides
    fun provideDeleteNewsUseCase(newsRepository: NewsRepository): DeleteNewsUseCase {
        return DeleteNewsUseCase(newsRepository)
    }

}