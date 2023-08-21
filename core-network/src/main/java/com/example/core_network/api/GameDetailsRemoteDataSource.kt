package com.example.core_network.api

import com.example.core_network.model.GameDetailsDto
import com.example.core_network.model.ImageDto
import javax.inject.Inject

class GameDetailsRemoteDataSource @Inject constructor(
    private val api: RawgApi
) {

    suspend fun gameDetails(id: Long): GameDetailsDto = api.gameDetails(id)

    suspend fun gameScreenshots(id: Long, number: Int): List<ImageDto> =
        api.gameScreenshots(id, number).results
}