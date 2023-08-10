package com.example.core_network.api

import com.example.core_network.api.params.GamesApiParams
import com.example.core_network.model.GameDto

interface GamesRemoteDataSource {

    fun updateParams(params: GamesApiParams, alreadyLoadedCount: Int)

    suspend fun initialLoading(params: GamesApiParams): List<GameDto>

    suspend fun loadMore(): List<GameDto>
}