package com.xlwe.news.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface NewsListDao {

    @Query("SELECT * FROM news_items")
    fun getNews(): Flow<List<NewsItemDbModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addNews(newsItemDbModel: NewsItemDbModel)

    @Query("DELETE FROM news_items WHERE id=:newsItemDbModel")
    suspend fun deleteNews(newsItemDbModel: Int)

}