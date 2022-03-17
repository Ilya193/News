package com.xlwe.news.data.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [NewsItemDbModel::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun newsListDao(): NewsListDao
}