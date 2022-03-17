package com.xlwe.news.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.xlwe.news.R
import com.xlwe.news.databinding.NewsItemCommonBinding
import com.xlwe.news.databinding.NewsItemFavoriteBinding
import com.xlwe.news.domain.model.Article
import com.xlwe.news.log

class NewsAdapter(
    private var news: List<Article>,
    private val onClick: OnClick
) : ListAdapter<Article, NewsAdapter.NewsViewHolder>(wordsComparator) {

    class NewsViewHolder(val binding: ViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {
        if (viewType == COMMON_TYPE)
            return NewsViewHolder(
                NewsItemCommonBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        else (viewType == FAVORITE_TYPE)
            return NewsViewHolder(
                NewsItemFavoriteBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
    }

    /*fun update(newList: List<Article>) {
        log("asdfasdfasdfasdf ${news.size} ${newList.size}")
        val diffUtilsCallback = DiffUtilsCallback(news, newList)
        val diffResult = DiffUtil.calculateDiff(diffUtilsCallback)
        news = newList
        diffResult.dispatchUpdatesTo(this)
    }*/

    override fun onBindViewHolder(holderNews: NewsViewHolder, position: Int) {
        val news = getItem(position)

        when (holderNews.binding) {
            is NewsItemCommonBinding -> {
                with(holderNews.binding) {
                    title.text = news.title
                    author.text = if (news.author == "null") "" else news.author
                    type.setImageResource(R.drawable.ic_favorite_border)

                    body.setOnClickListener {
                        onClick.clickBody(getItem(position))
                    }

                    type.setOnClickListener {
                        onClick.click(news, 1, position)
                    }

                    Glide.with(image.context)
                        .load(news.urlToImage)
                        .transform(CenterCrop(), RoundedCorners(20))
                        .into(image)
                }
            }
            is NewsItemFavoriteBinding -> {
                with(holderNews.binding) {
                    title.text = news.title
                    author.text = if (news.author == "null") "" else news.author
                    type.setImageResource(R.drawable.ic_favorite)

                    body.setOnClickListener {
                        onClick.clickBody(getItem(position))
                    }

                    type.setOnClickListener {
                        onClick.click(news, 2, position)
                    }

                    Glide.with(image.context)
                        .load(news.urlToImage)
                        .transform(CenterCrop(), RoundedCorners(20))
                        .into(image)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        val item = getItem(position)
        if (item.type == FAVORITE_TYPE)
            return FAVORITE_TYPE
        return COMMON_TYPE
    }

    /*override fun getItemCount(): Int {
        return news.size
    }*/

    companion object {
        private val wordsComparator = object : DiffUtil.ItemCallback<Article>() {
            override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem.title == newItem.title
            }

            override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
                return oldItem == newItem
            }
        }

        private const val COMMON_TYPE = 1
        private const val FAVORITE_TYPE = 2
    }

}