package com.example.rcg.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.core_network.api.GamesRemoteDataSource
import com.example.core_network.api.PagingState
import com.example.core_network.api.RawgApi
import com.example.core_network.api.params.GamesApiParams
import com.example.core_network.di.NetworkComponent
import com.example.core_network.model.GameDto
import com.example.rcg.R
import com.example.rcg.interactor.main.MainScreenInteractor
import com.example.rcg.model.base.ListItem
import com.example.rcg.model.game.GameThinItem
import com.example.rcg.model.game.GameWideItem
import com.example.rcg.model.game.GamesHorizontalItem
import com.example.rcg.model.game.ProgressThinItem
import com.example.rcg.model.game.ProgressWideItem
import com.example.rcg.util.ResourceProvider
import com.example.rcg.viewmodel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
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
}