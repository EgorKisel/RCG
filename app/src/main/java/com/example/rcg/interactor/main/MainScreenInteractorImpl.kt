package com.example.rcg.interactor.main

import com.example.core_network.api.GamesRemoteDataSource
import com.example.core_network.api.PagingState
import com.example.core_network.api.RawgApi
import com.example.rcg.model.base.ListItem
import com.example.rcg.model.game.GameThinItem
import com.example.rcg.model.game.GameWideItem
import com.example.rcg.model.game.GamesHorizontalItem
import com.example.rcg.model.game.ProgressThinItem
import com.example.rcg.model.game.ProgressWideItem
import com.example.rcg.repository.GameCategoryRepository
import com.example.rcg.repository.LatestReleasesGamesRepositoryImpl
import com.example.rcg.repository.MostAnticipatedGamesRepositoryImpl
import com.example.rcg.repository.RatedGamesRepositoryImpl
import com.example.rcg.repository.model.CategoryType
import com.example.rcg.repository.model.GameCategoryModel
import com.example.rcg.util.ResourceProvider
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject

class MainScreenInteractorImpl @Inject constructor(
    api: RawgApi,
    resources: ResourceProvider
) : MainScreenInteractor {

    private val mostAnticipatedGamesRepository: GameCategoryRepository =
        MostAnticipatedGamesRepositoryImpl(GamesRemoteDataSource(api), resources)
    private val latestReleasesGamesRepository: GameCategoryRepository =
        LatestReleasesGamesRepositoryImpl(GamesRemoteDataSource(api), resources)
    private val ratedGamesRepository: GameCategoryRepository =
        RatedGamesRepositoryImpl(GamesRemoteDataSource(api), resources)

    override fun data(): Flow<List<ListItem>> = combine(
        mostAnticipatedGamesRepository.data(),
        latestReleasesGamesRepository.data(),
        ratedGamesRepository.data()
    ) { anticipated, latest, rated ->
        listOf(
            mapToCategory(anticipated, true),
            mapToCategory(latest),
            mapToCategory(rated, true)
        )
    }

    override suspend fun initCategory(category: CategoryType) {
        when (category) {
            is CategoryType.MostAnticipated -> mostAnticipatedGamesRepository.init()
            is CategoryType.LatestReleases -> latestReleasesGamesRepository.init()
            is CategoryType.Rated -> ratedGamesRepository.init()
            else -> {}
        }
    }

    private fun mapToCategory(model: GameCategoryModel, wide: Boolean = false): ListItem =
        when (model.dataState) {
            is PagingState.Initial -> {
                GamesHorizontalItem(
                    title = model.title,
                    category = model.category,
                    games = IntRange(1, 3).map { if (wide) ProgressWideItem else ProgressThinItem }
                )
            }

            is PagingState.Content -> {
                GamesHorizontalItem(
                    title = model.title,
                    category = model.category,
                    games = model.dataState.data.map {
                        if (wide) {
                            GameWideItem(
                                id = it.id,
                                title = it.title,
                                image = it.image
                            )
                        } else {
                            GameThinItem(
                                id = it.id,
                                title = it.title,
                                image = it.image
                            )
                        }
                    }
                )
            }

            else -> {
                throw IllegalArgumentException("Wrong paging state ${model.dataState}")
            }
        }
}