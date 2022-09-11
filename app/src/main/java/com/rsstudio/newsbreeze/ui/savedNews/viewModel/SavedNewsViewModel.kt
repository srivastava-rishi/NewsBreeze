package com.rsstudio.newsbreeze.ui.savedNews.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rsstudio.newsbreeze.data.local.database.SavedNewsDatabase
import com.rsstudio.newsbreeze.data.local.entity.SavedNewsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
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
    private val _localNewsData: MutableLiveData<MutableList<SavedNewsEntity>> = MutableLiveData()
    val localNewsData: LiveData<MutableList<SavedNewsEntity>> get() = _localNewsData


    init {
        getNews()
    }

    private fun getNews(){

        viewModelScope.launch(Dispatchers.Default) {
            localRepository.savedNewsDao().getAllSavedNews().let {
                _localNewsData.postValue(it)
            }

        }
    }

}