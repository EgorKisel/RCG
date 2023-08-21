package com.example.rcg.ui.base.model.game

import com.example.rcg.model.base.ListItem
import com.example.rcg.repository.model.CategoryType

data class ErrorThinItem(val categoryType: CategoryType) : ListItem {
    override val itemId: Long = 1
}
