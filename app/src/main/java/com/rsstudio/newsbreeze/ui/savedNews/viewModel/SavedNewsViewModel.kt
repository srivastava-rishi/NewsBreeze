package com.rsstudio.newsbreeze.ui.savedNews.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsstudio.newsbreeze.data.local.database.SavedNewsDatabase
import com.rsstudio.newsbreeze.data.local.entity.SavedNewsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SavedNewsViewModel
@Inject
constructor(
    private val localRepository: SavedNewsDatabase,
) : ViewModel() {

    var logTag = "@SavedNewsViewModel"

    // the pattern that i usually follow
    private val _localNewsData: MutableLiveData<List<SavedNewsEntity>> = MutableLiveData()
    val localNewsData: LiveData<List<SavedNewsEntity>> get() = _localNewsData


    init {
        getNews()
    }

    private fun getNews(){

        viewModelScope.launch {
            val result = localRepository.savedNewsDao().getAllSavedNews()
            _localNewsData.value = result
        }
    }

}