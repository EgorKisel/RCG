package com.example.rcg.viewmodel.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rcg.interactor.details.GameDetailsInteractor
import com.example.rcg.viewmodel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GameDetailsViewModel @Inject constructor(
    private val initialModel: GameDetailsScreenModel.Initial,
    private val interactor: GameDetailsInteractor
) : BaseViewModel() {

    private val _data = MutableLiveData<GameDetailsScreenModel>(initialModel)
    val data: LiveData<GameDetailsScreenModel> = _data

    init {
        init()
    }

    fun refresh() {
        _data.postValue(initialModel)
        init()
    }

    private fun init() {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            _data.postValue(interactor.data())
        }
    }
}