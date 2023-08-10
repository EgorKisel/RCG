package com.example.rcg.interactor.main

import com.example.core_network.api.GamesRemoteDataSourceImpl
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
        MostAnticipatedGamesRepositoryImpl(GamesRemoteDataSourceImpl(api), resources)
    private val latestReleasesGamesRepository: GameCategoryRepository =
        LatestReleasesGamesRepositoryImpl(GamesRemoteDataSourceImpl(api), resources)
    private val ratedGamesRepository: GameCategoryRepository =
        RatedGamesRepositoryImpl(GamesRemoteDataSourceImpl(api), resources)

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

    override suspend fun tryToLoadMore(category: CategoryType, index: Int) {
        when (category) {
            is CategoryType.MostAnticipated -> mostAnticipatedGamesRepository.loadMore(index)
            is CategoryType.LatestReleases -> latestReleasesGamesRepository.loadMore(index)
            is CategoryType.Rated -> ratedGamesRepository.loadMore(index)
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

            is PagingState.Paging -> {
                GamesHorizontalItem(
                    title = model.title,
                    category = model.category,
                    games = model.dataState.availableContent.map {
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
                        .plus(if (wide) ProgressWideItem else ProgressThinItem)
                )
            }

            else -> {
                throw IllegalArgumentException("Wrong paging state ${model.dataState}")
            }
        }
}