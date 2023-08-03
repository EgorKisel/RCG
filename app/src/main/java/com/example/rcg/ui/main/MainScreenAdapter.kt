package com.example.rcg.ui.main

import com.example.rcg.model.base.ListItem
import com.example.rcg.viewmodel.base.BaseDiffUtilItemCallback
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter

class MainScreenAdapter : AsyncListDifferDelegationAdapter<ListItem>(BaseDiffUtilItemCallback()) {
    init {
        delegatesManager.addDelegate(MainScreenDelegates.gamesHorizontalDelegate)
    }
}