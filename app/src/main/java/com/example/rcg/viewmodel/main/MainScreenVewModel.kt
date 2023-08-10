package com.example.rcg.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rcg.interactor.main.MainScreenInteractor
import com.example.rcg.model.base.ListItem
import com.example.rcg.model.game.GamesHorizontalItem
import com.example.rcg.repository.model.CategoryType
import com.example.rcg.viewmodel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenVewModel @Inject constructor(
    private val interactor: MainScreenInteractor
) : BaseViewModel() {

    private val _data = MutableLiveData<List<ListItem>>()
    val data: LiveData<List<ListItem>> = _data

    init {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.data().collect { _data.postValue(it) }
        }
    }

    fun initCategory(item: GamesHorizontalItem) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.initCategory(item.category)
        }
    }

    fun readyToLoadMore(category: CategoryType, index: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            interactor.tryToLoadMore(category, index)
        }
    }
}