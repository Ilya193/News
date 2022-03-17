package com.xlwe.news.presentation.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.xlwe.news.R
import com.xlwe.news.databinding.ActivityMainBinding
import com.xlwe.news.domain.model.Article
import com.xlwe.news.presentation.fragments.FavoriteFragment
import com.xlwe.news.presentation.fragments.HomeFragment
import com.xlwe.news.presentation.viewmodels.MainViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val mainViewModel by viewModels<MainViewModel>()

    private var saveNews = emptyList<Article>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, HomeFragment.newInstance())
            .commit()

        observeViewModel()
        setClickListeners()
    }

    private fun observeViewModel() {
        mainViewModel.transitionNews.observe(this) {
            saveNews = it
        }
    }

    private fun setClickListeners() {
        binding.bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, HomeFragment.newInstance())
                        .commit()
                }

                R.id.favorite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragmentContainer, FavoriteFragment.newInstance())
                        .commit()

                }
            }

            true
        }
    }
}