package com.example.rcg.repository

import com.example.core_network.api.GamesRemoteDataSource
import com.example.core_network.api.params.GamesApiParams
import com.example.rcg.R
import com.example.rcg.repository.model.CategoryType
import com.example.rcg.repository.model.GameCategoryModel
import com.example.rcg.util.ResourceProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class RatedGamesRepositoryImpl @Inject constructor(
    private val dataSource: GamesRemoteDataSource,
    private val resource: ResourceProvider
) : GameCategoryRepository {

    override fun data(): Flow<GameCategoryModel> = dataSource.channel.map {
        GameCategoryModel(
            title = resource.string(R.string.most_rated_in_2024),
            category = CategoryType.Rated,
            dataState = it
        )
    }

    override suspend fun init() {
        dataSource.initialLoading(
            GamesApiParams(
                dates = "2023-01-01,2023-08-04",
                ordering = "-rated"
            )
        )
    }
}