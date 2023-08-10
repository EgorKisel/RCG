package com.example.rcg.repository

import com.example.core_network.api.GamesRemoteDataSourceImpl
import com.example.core_network.api.params.GamesApiParams
import com.example.rcg.R
import com.example.rcg.repository.model.CategoryType
import com.example.rcg.repository.model.GameCategoryModel
import com.example.rcg.util.ResourceProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class LatestReleasesGamesRepositoryImpl @Inject constructor(
    private val dataSource: GamesRemoteDataSourceImpl,
    private val resource: ResourceProvider
) : GameCategoryRepository {

    override fun data(): Flow<GameCategoryModel> = dataSource.channel.map {
        GameCategoryModel(
            title = resource.string(R.string.latest_releases),
            category = CategoryType.LatestReleases,
            dataState = it
        )
    }

    override suspend fun init() {
        dataSource.initialLoading(GamesApiParams(
            dates = "2023-07-01,2023-08-07"
        ))
    }

    override suspend fun loadMore(index: Int) {
        dataSource.loadMore(index)
    }
}