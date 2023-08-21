package com.example.rcg.repository.details

import com.example.core_network.api.GameDetailsRemoteDataSource
import com.example.rcg.model.GameDetailsModel
import javax.inject.Inject

class GameDetailsRepositoryImpl @Inject constructor(
    private val remoteDataSource: GameDetailsRemoteDataSource,
) : GameDetailsRepository {

    override suspend fun data(id: Long): GameDetailsModel {
        val details = remoteDataSource.gameDetails(id)
        val screenshots = remoteDataSource.gameScreenshots(id, DEFAULT_SCREENSHOTS_MAX_NUMBER)
        return GameDetailsModel(
            id = details.id,
            title = details.title,
            image = details.image,
            description = details.description,
            screenshots = screenshots.map { it.image }
        )
    }

    private companion object {
        const val DEFAULT_SCREENSHOTS_MAX_NUMBER = 5
    }
}