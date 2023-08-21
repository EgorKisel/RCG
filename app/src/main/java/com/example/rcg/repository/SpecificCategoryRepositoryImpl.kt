package com.example.rcg.repository

import com.example.core_network.api.GamesRemoteDataSource
import com.example.core_network.api.PagingState
import com.example.core_network.model.GameDto
import com.example.rcg.persist.GamesPersistDataSource
import com.example.rcg.repository.model.CategoryType
import com.example.rcg.repository.model.GameCategoryModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class SpecificCategoryRepositoryImpl(
    private val remoteDataSource: GamesRemoteDataSource,
    private val persistDataSource: GamesPersistDataSource,
    private val categoryTitle: String,
    private val categoryType: CategoryType
) : GameCategoryRepository {

    private val dataState = MutableStateFlow(mapToModel(PagingState.Initial))

    override fun data(): Flow<GameCategoryModel> = dataState

    override suspend fun init() {
        val state = dataState.value.dataState
        if (state is PagingState.Initial || state is PagingState.Error || state is PagingState.Persist) {
            try {
                val data = remoteDataSource.initialLoading(categoryType.filters)
                dataState.emit(mapToModel(PagingState.Content(data)))
                persistDataSource.clear(categoryType)
                persistDataSource.insert(data, categoryType)
            } catch (e: Exception) {
                val persistData = persistDataSource.games(categoryType)
                if (persistData.isEmpty()) {
                    dataState.emit(mapToModel(PagingState.Error(e)))
                } else {
                    dataState.emit(mapToModel(PagingState.Persist(persistData)))
                }
                throw e
            }
        }
    }

    override suspend fun tryToLoadMore(index: Int) {
        val state = dataState.value.dataState
        if (state is PagingState.Content && index == state.data.size - 5) {
            loadMore()
        }
    }

    override suspend fun refresh(force: Boolean) {
        if (force) {
            init()
        } else {
            loadMore()
        }
    }

    private suspend fun loadMore() {
        val currentData = (dataState.value.dataState as? PagingState.Content)?.data
            ?: (dataState.value.dataState as? PagingState.Persist)?.data
            ?: return
        dataState.emit(mapToModel(PagingState.Paging(currentData)))
        try {
            val data = remoteDataSource.loadMore()
            dataState.emit(mapToModel(PagingState.Content(currentData.plus(data))))
            persistDataSource.insert(data, categoryType)
        } catch (e: Exception) {
            val persistData = persistDataSource.games(categoryType)
            if (persistData.isEmpty()) {
                dataState.emit(mapToModel(PagingState.Error(e)))
            } else {
                dataState.emit(mapToModel(PagingState.Persist(persistData)))
            }
            throw e
        }
    }

    private fun mapToModel(state: PagingState<List<GameDto>>) = GameCategoryModel(
        title = categoryTitle,
        category = categoryType,
        dataState = state
    )
}