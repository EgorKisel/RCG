package com.example.rcg.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.rcg.model.base.ListItem
import com.example.rcg.model.game.GameThinItem
import com.example.rcg.model.game.GameWideItem
import com.example.rcg.model.game.GamesHorizontalItem
import com.example.rcg.model.game.ProgressThinItem
import com.example.rcg.model.game.ProgressWideItem
import com.example.rcg.viewmodel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainScreenVewModel : BaseViewModel() {

    private val _data = MutableLiveData<List<ListItem>>()
    val data: LiveData<List<ListItem>> = _data

    init {
        viewModelScope.launch(Dispatchers.IO) {
            _data.postValue(getLoaders())
            val items = getItems()
            _data.postValue(items)
        }
    }

    private fun getLoaders(): List<ListItem> {
        return listOf(
            GamesHorizontalItem(
                title = "Wide games title",
                games = IntRange(1, 2).map { ProgressWideItem }
            ),
            GamesHorizontalItem(
                title = "Thin games title",
                games = IntRange(1, 3).map { ProgressThinItem }
            ),
            GamesHorizontalItem(
                title = "Wide games title",
                games = IntRange(1, 2).map { ProgressWideItem }
            )
        )
    }

    private suspend fun getItems(): List<ListItem> {
        delay(2000L)
        return listOf(
            GamesHorizontalItem(
                title = "Wide games title",
                games = IntRange(1, 20).map { GameWideItem(it.toLong(), "Game title $it") }
            ),
            GamesHorizontalItem(
                title = "Thin games title",
                games = IntRange(1, 20).map { GameThinItem(it.toLong(), "Game title $it") }
            ),
            GamesHorizontalItem(
                title = "Wide games title",
                games = IntRange(1, 20).map { GameWideItem(it.toLong(), "Game title $it") }
            )
        )
    }
}