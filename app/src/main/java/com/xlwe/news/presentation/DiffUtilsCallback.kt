package com.xlwe.news.presentation

import androidx.recyclerview.widget.DiffUtil
import com.xlwe.news.domain.model.Article
import com.xlwe.news.log

class DiffUtilsCallback(
    private val oldList: List<Article>,
    private val newList: List<Article>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].type == newList[oldItemPosition].type
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[oldItemPosition]
    }
}