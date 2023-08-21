package com.example.rcg.interactor.main

import com.example.rcg.model.base.ListItem
import com.example.rcg.repository.model.CategoryType
import kotlinx.coroutines.flow.Flow

interface MainScreenInteractor {

    fun data(): Flow<List<ListItem>>
    suspend fun initCategory(category: CategoryType)
    suspend fun tryToLoadMore(category: CategoryType, index: Int)
    suspend fun refresh(category: CategoryType?)
}