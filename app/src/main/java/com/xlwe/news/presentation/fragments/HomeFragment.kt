package com.xlwe.news.presentation.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.xlwe.news.R
import com.xlwe.news.databinding.FragmentHomeBinding
import com.xlwe.news.domain.NetworkResult
import com.xlwe.news.domain.model.Article
import com.xlwe.news.log
import com.xlwe.news.presentation.NewsAdapter
import com.xlwe.news.presentation.OnClick
import com.xlwe.news.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(), OnClick {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding!!

    private val mainViewModel: MainViewModel by activityViewModels()
    private lateinit var newsAdapter: NewsAdapter
    private val mainListNews = mutableListOf<Article>()

    private var tempData = mutableListOf<Article>()
    private var positionDelete = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        newsAdapter = NewsAdapter(emptyList(), this)
        binding.recyclerView.adapter = newsAdapter
    }

    private fun observeViewModel() {
        mainViewModel.news.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Loading -> binding.progressBar.visibility = View.VISIBLE
                is NetworkResult.Success -> {

                    mainListNews.clear()
                    result.list?.forEach { article ->
                        mainListNews.add(article)
                    }

                    binding.progressBar.visibility = View.GONE
                    newsAdapter.submitList(mainListNews.toList())
                }
            }
        }

        mainViewModel.newsDatabase.observe(viewLifecycleOwner) { result ->
            when (result) {
                is NetworkResult.Loading -> {}
                is NetworkResult.Success -> {
                    tempData = result.list?.toMutableList() ?: mutableListOf()

                    //log("DATA ${tempData}")

                    var i = 0
                    var j = 0
                    while (i < mainListNews.size) {
                        while (j < tempData.size) {
                            if (tempData[j].title == mainListNews[i].title) {
                                //isChange = true
                                //log("asdfadfsd ${mainListNews[i]}           ${tempData[j]}")
                                mainListNews[i] = tempData[j]
                            }

                            j++
                        }


                        i++
                        j = 0
                    }

                    newsAdapter.submitList(mainListNews.toList())

                    /*if (tempData.isNotEmpty() && mainListNews.isNotEmpty()) {
                        if (positionDelete != -1) {
                            newsAdapter.notifyItemChanged(positionDelete)
                            positionDelete = -1
                        }

                        var i = 0
                        var j = 0
                        var isChange = false
                        var index = -1

                        while (i < mainListNews.size) {
                            while (j < tempData.size) {
                                if (tempData[j].title == mainListNews[i].title) {
                                    isChange = true
                                }

                                j++
                            }

                            if (isChange) {
                                mainListNews[i].type = 2
                                index = i
                                isChange = false
                            } else {
                                mainListNews[i].type = 1
                            }

                            i++
                            j = 0
                        }

                        //newsAdapter.submitList(mainListNews.toList())

                        if (index != -1) {
                            log("index $index ${mainListNews[index]}")
                            newsAdapter.notifyItemChanged(index)
                            log("ТЕСТТЕСТ")
                        }
                    } else {
                        mainListNews.forEach {
                            it.type = 1
                        }

                        if (positionDelete != -1) {
                            log("1")
                            newsAdapter.notifyItemChanged(positionDelete)
                            positionDelete = -1
                        }
                        else {
                            log("2")
                            newsAdapter.submitList(mainListNews.toList())
                        }
                    }*/
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun click(article: Article, type: Int, position: Int) {
        if (type == 1)
            mainViewModel.insert(article)
        else {
            mainViewModel.delete(article)
            positionDelete = position
            mainListNews[position].type = 1
            newsAdapter.notifyItemChanged(position)
            /*var deleteArticle: Article? = null
            tempData.forEach {
                if (it.title == article.title) {
                    deleteArticle = it
                    log("TEST $it")
                }
            }

            if (deleteArticle != null) {
                positionDelete = position
                mainViewModel.delete(deleteArticle!!)
            }*/
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