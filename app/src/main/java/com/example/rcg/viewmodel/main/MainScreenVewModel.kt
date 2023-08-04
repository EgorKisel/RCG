package com.example.rcg.viewmodel.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.core_network.api.RawgApi
import com.example.core_network.di.NetworkComponent
import com.example.rcg.R
import com.example.rcg.model.base.ListItem
import com.example.rcg.model.game.GameThinItem
import com.example.rcg.model.game.GameWideItem
import com.example.rcg.model.game.GamesHorizontalItem
import com.example.rcg.model.game.ProgressThinItem
import com.example.rcg.model.game.ProgressWideItem
import com.example.rcg.util.ResourceProvider
import com.example.rcg.viewmodel.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainScreenVewModel @Inject constructor(
    private val resources: ResourceProvider,
    // private val api: RawgApi
) : BaseViewModel() {

    private val api = NetworkComponent.createApi()
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
                title = resources.string(R.string.most_anticipated),
                games = IntRange(1, 2).map { ProgressWideItem }
            ),
            GamesHorizontalItem(
                title = resources.string(R.string.latest_releases),
                games = IntRange(1, 3).map { ProgressThinItem }
            ),
            GamesHorizontalItem(
                title = resources.string(R.string.most_rated_in_2024),
                games = IntRange(1, 2).map { ProgressWideItem }
            )
        )
    }

    private suspend fun getItems(): List<ListItem> {
        val mostAnticipatedResponse = api.games(
            "721575b77ad74531b6066e37437c0b07", mapOf(
                "dates" to "2023-08-03,2024-08-03",
                "ordering" to "-added"
            )
        )
        val latestReleasesResponse = api.games(
            "721575b77ad74531b6066e37437c0b07", mapOf(
                "dates" to "2023-06-01,2023-08-03"
            )
        )
        val mostRatedResponse = api.games(
            "721575b77ad74531b6066e37437c0b07", mapOf(
                "dates" to "2023-01-01,2023-08-04",
                "ordering" to "-rated"
            )
        )

        val mostAnticipatedItems = mostAnticipatedResponse.results.map {
            GameWideItem(
                id = it.id,
                title = it.title,
                image = it.image
            )
        }

        val latestReleasesItems = latestReleasesResponse.results.map {
            GameThinItem(
                id = it.id,
                title = it.title,
                image = it.image
            )
        }

        val mostRatedItems = mostRatedResponse.results.map {
            GameWideItem(
                id = it.id,
                title = it.title,
                image = it.image
            )
        }

        return listOf(
            GamesHorizontalItem(
                title = "The most anticipated",
                games = mostAnticipatedItems
            ),
            GamesHorizontalItem(
                title = "Latest releases",
                games = latestReleasesItems
            ),
            GamesHorizontalItem(
                title = "The most rated in 2023",
                games = mostRatedItems
            )
        )
    }
}