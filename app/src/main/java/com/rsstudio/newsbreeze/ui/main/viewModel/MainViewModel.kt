package com.rsstudio.newsbreeze.ui.main.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsstudio.newsbreeze.data.network.model.News
import com.rsstudio.newsbreeze.data.network.repository.MainRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel
@Inject
constructor(
    private val repository: MainRepository,
) : ViewModel() {

    var logTag = "@MainViewModel"

    // the pattern that i usually follow
    private val _newsData: MutableLiveData<Response<News>> = MutableLiveData()
    val newsData: LiveData<Response<News>> get() = _newsData


    init {
        getNews()
    }

    private fun getNews(){

        viewModelScope.launch {
            val result = repository.getNewsData()
            _newsData.value = result
        }
    }

}