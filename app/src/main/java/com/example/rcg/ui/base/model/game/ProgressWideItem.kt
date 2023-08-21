package com.example.rcg.ui.base.model.game

import com.example.rcg.model.base.ListItem

data class ProgressWideItem(val withLoader: Boolean) : ListItem {
    override val itemId: Long = 0
}