package com.example.rcg.persist

import com.example.core_network.model.GameDto
import com.example.rcg.di.ScreenScope
import com.example.rcg.persist.model.GameEntity
import com.example.rcg.repository.model.CategoryType
import javax.inject.Inject

@ScreenScope
class GamesPersistDataSource @Inject constructor(
    private val gamesDao: GamesDao
) {

    fun games(categoryType: CategoryType): List<GameDto> = when (categoryType) {
        is CategoryType.MostAnticipated -> gamesDao.getMostAnticipated()
        is CategoryType.LatestReleases -> gamesDao.getLatestReleases()
        is CategoryType.Rated -> gamesDao.getRated()
    }.map { entity -> entity.toDto() }

    fun insert(games: List<GameDto>, categoryType: CategoryType) {
        gamesDao.insertAll(
            games.map { it.toEntity(categoryType.internalId) }
        )
    }

    fun clear(categoryType: CategoryType) {
        gamesDao.clear(categoryType.internalId)
    }

    private fun GameEntity.toDto() = GameDto(
        id = id,
        title = title,
        image = image,
        released = released,
        added = added,
        rating = rating,
    )

    private fun GameDto.toEntity(categoryId: Int) = GameEntity(
        id = id,
        title = title,
        image = image,
        released = released,
        added = added,
        rating = rating,
        categoryId = categoryId,
    )
}