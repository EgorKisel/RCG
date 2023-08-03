package com.example.rcg.model.game

import com.example.rcg.model.base.ListItem

data class GamesHorizontalItem(
    val title: String,
    val games: List<ListItem>
) : ListItem {
    override val itemId: Long = title.hashCode().toLong()
}