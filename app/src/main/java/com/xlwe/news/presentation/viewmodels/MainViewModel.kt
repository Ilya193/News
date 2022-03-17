package com.xlwe.news.presentation.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.xlwe.news.domain.NetworkResult
import com.xlwe.news.domain.model.Article
import com.xlwe.news.domain.usecases.DeleteNewsUseCase
import com.xlwe.news.domain.usecases.GetNewsFromDatabaseUseCase
import com.xlwe.news.domain.usecases.GetNewsUseCase
import com.xlwe.news.domain.usecases.InsertUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getNewsUseCase: GetNewsUseCase,
    private val insertUseCase: InsertUseCase,
    private val getNewsFromDatabaseUseCase: GetNewsFromDatabaseUseCase,
    private val deleteNewsUseCase: DeleteNewsUseCase
) : ViewModel() {

    private val _news: MutableLiveData<NetworkResult> =
        MutableLiveData(NetworkResult.Loading())

    val news: LiveData<NetworkResult>
        get() = _news

    private val _transitionNews: MutableLiveData<List<Article>> =
        MutableLiveData(emptyList())

    val transitionNews: LiveData<List<Article>>
        get() = _transitionNews

    private val _newsDatabase: MutableLiveData<NetworkResult> =
        MutableLiveData(NetworkResult.Loading())

    val newsDatabase: LiveData<NetworkResult>
        get() = _newsDatabase

    private val _saveArticle = MutableLiveData<Article>()
    val saveArticle: LiveData<Article>
        get() = _saveArticle

    private var tempDataNetwork = mutableListOf<Article>()

    init {
        viewModelScope.launch(Dispatchers.IO) {
            getNewsUseCase.getNews().collect {
                _news.postValue(it)
            }
            getNewsFromDatabaseUseCase.getNewsFromDatabase().collect {
                _newsDatabase.postValue(it)
            }
        }
    }

    fun insert(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            insertUseCase.insert(article)
        }
    }

    fun delete(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteNewsUseCase.deleteNews(article)
        }
    }

    fun saveArticle(article: Article) {
        _saveArticle.value = article
    }

}