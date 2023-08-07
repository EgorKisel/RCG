package com.example.rcg.repository.model

import com.example.core_network.api.PagingState
import com.example.core_network.model.GameDto

data class GameCategoryModel(
    val title: String,
    val category: CategoryType,
    val dataState: PagingState<List<GameDto>>
)