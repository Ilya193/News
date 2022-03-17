package com.xlwe.news.presentation

import com.xlwe.news.domain.model.Article

interface OnClick {
    fun click(article: Article, type: Int, position: Int)
    fun clickBody(article: Article)
}