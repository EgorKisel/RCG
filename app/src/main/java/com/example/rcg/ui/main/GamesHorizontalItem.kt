package com.example.rcg.ui.main

import com.example.rcg.ui.base.ListItem

data class GamesHorizontalItem(
    val title: String,
    val games: List<ListItem>
) : ListItem