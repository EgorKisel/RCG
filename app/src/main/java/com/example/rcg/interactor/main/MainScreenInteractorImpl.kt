package com.example.rcg.interactor.main

import com.example.core_network.api.PagingState
import com.example.core_network.model.GameDto
import com.example.rcg.model.base.ListItem
import com.example.rcg.model.game.GameThinItem
import com.example.rcg.model.game.GameWideItem
import com.example.rcg.model.game.GamesHorizontalItem
import com.example.rcg.repository.GameCategoryRepository
import com.example.rcg.repository.model.CategoryType
import com.example.rcg.repository.model.GameCategoryModel
import com.example.rcg.ui.base.model.game.ProgressWideItem
import com.example.rcg.ui.base.model.game.ProgressThinItem
import com.example.rcg.ui.base.model.game.ErrorThinItem
import com.example.rcg.ui.base.model.game.ErrorWideItem
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import javax.inject.Named

class MainScreenInteractorImpl @Inject constructor(
    @Named("mostAnticipated") private val mostAnticipatedGamesRepository: GameCategoryRepository,
    @Named("latestReleases") private val latestReleasesGamesRepository: GameCategoryRepository,
    @Named("rated") private val ratedGamesRepository: GameCategoryRepository,
) : MainScreenInteractor {

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
            is CategoryType.MostAnticipated -> mostAnticipatedGamesRepository.tryToLoadMore(index)
            is CategoryType.LatestReleases -> latestReleasesGamesRepository.tryToLoadMore(index)
            is CategoryType.Rated -> ratedGamesRepository.tryToLoadMore(index)
            else -> {}
        }
    }

    override suspend fun refresh(category: CategoryType?) {
        when (category) {
            CategoryType.LatestReleases -> latestReleasesGamesRepository.refresh(false)
            CategoryType.MostAnticipated -> mostAnticipatedGamesRepository.refresh(false)
            CategoryType.Rated -> ratedGamesRepository.refresh(false)
            null -> {
                coroutineScope {
                    awaitAll(
                        async { mostAnticipatedGamesRepository.refresh(true) },
                        async { latestReleasesGamesRepository.refresh(true) },
                        async { ratedGamesRepository.refresh(true) },
                    )
                }
            }
        }
    }

    private fun mapToCategory(
        model: GameCategoryModel,
        wide: Boolean = false
    ): GamesHorizontalItem =
        when (model.dataState) {
            is PagingState.Initial -> {
                GamesHorizontalItem(
                    title = model.title,
                    category = model.category,
                    games = IntRange(1, 3)
                        .map { if (wide) ProgressWideItem(false)
                        else ProgressThinItem(false)
                        }
                )
            }

            is PagingState.Content -> {
                GamesHorizontalItem(
                    title = model.title,
                    category = model.category,
                    games = model.dataState.data.toItems(wide)
                )
            }

            is PagingState.Paging -> {
                GamesHorizontalItem(
                    title = model.title,
                    category = model.category,
                    games = model.dataState.availableContent.toItems(wide)
                        .plus(if (wide) ProgressWideItem(true)
                        else ProgressThinItem(true)
                        )
                )
            }

            is PagingState.Persist -> {
                GamesHorizontalItem(
                    title = model.title,
                    category = model.category,
                    games = model.dataState.data.toItems(wide)
                        .plus(if (wide) ErrorWideItem(model.category) else ErrorThinItem(model.category))
                )
            }

            is PagingState.Error -> {
                GamesHorizontalItem(
                    title = model.title,
                    category = model.category,
                    games = listOf(if (wide) ErrorWideItem(model.category) else ErrorThinItem(model.category))
                )
            }

            else -> throw IllegalArgumentException("Wrong paging state ${model.dataState}")
        }
    private fun List<GameDto>.toItems(wide: Boolean) = map {
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

}