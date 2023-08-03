package com.example.rcg.model.game

import com.example.rcg.model.base.ListItem

data class GameThinItem(
    val id: Long,
    val title: String,
    val image: String
) : ListItem {
    override val itemId: Long = id
}
