package com.example.rcg.model.game

import com.example.rcg.model.base.ListItem
import com.example.rcg.repository.model.CategoryType

data class GamesHorizontalItem(
    val title: String,
    val category: CategoryType,
    val games: List<ListItem>
) : ListItem {
    override val itemId: Long = title.hashCode().toLong()
}