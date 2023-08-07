package com.example.rcg.repository

import com.example.rcg.repository.model.GameCategoryModel
import kotlinx.coroutines.flow.Flow

interface GameCategoryRepository {

    fun data(): Flow<GameCategoryModel>
    suspend fun init()
}