package com.xlwe.news.presentation.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.xlwe.news.R
import com.xlwe.news.databinding.FragmentFavoriteBinding
import com.xlwe.news.domain.NetworkResult
import com.xlwe.news.domain.model.Article
import com.xlwe.news.presentation.adapters.NewsAdapter
import com.xlwe.news.presentation.OnClick
import com.xlwe.news.presentation.viewmodels.MainViewModel

class FavoriteFragment : Fragment(), OnClick {
    private var _binding: FragmentFavoriteBinding? = null
    private val binding: FragmentFavoriteBinding
        get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var newsAdapter: NewsAdapter
    private val mainListNews = mutableListOf<Article>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        initRecyclerView()
    }

    private fun observeViewModel() {
        mainViewModel.newsDatabase.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Loading -> {

                }
                is NetworkResult.Success -> {
                    mainListNews.clear()
                    result.list?.forEach { article ->
                        mainListNews.add(article)
                    }

                    //log("FAVORITE $mainListNews")

                    newsAdapter.submitList(mainListNews.toList())

                    /*newsAdapter.submitList(mainListNews.toList())*/
                }
            }
        }
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter(emptyList(), this)
        binding.recyclerView.adapter = newsAdapter
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = FavoriteFragment()
    }

    override fun click(article: Article, type: Int, position: Int) {
        if (type == 2) {
            mainViewModel.delete(article)
        }
    }

    override fun clickBody(article: Article) {
        mainViewModel.saveArticle(article)
        parentFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, DetailFragment.newInstance())
            .addToBackStack(null)
            .commit()
    }
}