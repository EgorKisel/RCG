package com.example.rcg.ui.main

import com.example.rcg.model.base.ListItem
import com.example.rcg.model.game.GamesHorizontalItem
import com.example.rcg.repository.model.CategoryType
import com.example.rcg.viewmodel.base.BaseDiffUtilItemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MainScreenAdapter(
    onItemBind: (GamesHorizontalItem) -> Unit,
    onReadyToLoadMore: (CategoryType, Int) -> Unit
) : AsyncListDifferDelegationAdapter<ListItem>(BaseDiffUtilItemCallback()) {
    init {
        delegatesManager.addDelegate(
            MainScreenDelegates.gamesHorizontalDelegate(
                onItemBind = onItemBind,
                onReadyToLoadMore = onReadyToLoadMore
            )
        )
    }
}