package com.example.rcg.ui.main

import com.example.rcg.model.base.ListItem
import com.example.rcg.viewmodel.base.BaseDiffUtilItemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class GameCardsAdapter : AsyncListDifferDelegationAdapter<ListItem>(BaseDiffUtilItemCallback()) {
    init {
        delegatesManager.addDelegate(MainScreenDelegates.wideGameDelegate)
            .addDelegate(MainScreenDelegates.thinGameDelegate)
            .addDelegate(MainScreenDelegates.wideProgressDelegate)
            .addDelegate(MainScreenDelegates.thinProgressDelegate)
    }
}
