package com.xlwe.news.domain

import com.xlwe.news.domain.model.Article

sealed class NetworkResult(
    val list: List<Article>? = null,
    val message: String? = null
) {

    class Success(listNews: List<Article>) : NetworkResult(listNews)
    class Error(message: String) : NetworkResult(null, message)
    class Loading() : NetworkResult()

}
