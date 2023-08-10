package com.example.rcg.ui.main

import com.example.rcg.model.base.ListItem
import com.example.rcg.repository.model.CategoryType
import com.example.rcg.viewmodel.base.BaseDiffUtilItemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class GameCardsAdapter(
    onReadyToLoadMore: (Int) -> Unit
) : AsyncListDifferDelegationAdapter<ListItem>(BaseDiffUtilItemCallback()) {
    init {
        setHasStableIds(true)
        delegatesManager.addDelegate(MainScreenDelegates.wideGameDelegate(onReadyToLoadMore))
            .addDelegate(MainScreenDelegates.thinGameDelegate(onReadyToLoadMore))
            .addDelegate(MainScreenDelegates.wideProgressDelegate())
            .addDelegate(MainScreenDelegates.thinProgressDelegate())
    }

    override fun getItemId(position: Int): Long {
        return items[position].itemId
    }
}
