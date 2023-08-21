package com.example.rcg.ui.base.model

import com.example.rcg.model.base.ListItem

data class ScreenshotItem(val image: String) : ListItem {
    override val itemId: Long = image.hashCode().toLong()
}
