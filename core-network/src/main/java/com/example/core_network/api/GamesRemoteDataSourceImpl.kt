package com.example.core_network.api

import com.example.core_network.api.params.GamesApiParams
import com.example.core_network.model.GameDto
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import java.lang.IllegalStateException
import javax.inject.Inject

class GamesRemoteDataSourceImpl @Inject constructor(
    private val api: RawgApi
) : GamesRemoteDataSource {

    private var params: GamesApiParams? = null
    internal var page = 1
        private set

    override fun updateParams(params: GamesApiParams, alreadyLoadedCount: Int) {
        this.params = params
        if (alreadyLoadedCount < 0) throw IllegalArgumentException("Already loaded count must be >= 0")
        this.page = (alreadyLoadedCount / (DEFAULT_PAGE_SIZE + 1)) + 1
    }

    override suspend fun initialLoading(params: GamesApiParams): List<GameDto> {
        val response = api.games(params.applyPagingParams())
        this.params = params
        return response.results
    }

    override suspend fun loadMore(): List<GameDto> {
        val params = this.params ?: throw IllegalStateException("There is no initial data")
        val response = api.games(params.applyPagingParams(page = page + 1))
        page += 1
        return response.results
    }

    private fun GamesApiParams.applyPagingParams(page: Int = 1): Map<String, String> =
        toMap().toMutableMap().apply {
            put("page", page.toString())
            put("page_size", DEFAULT_PAGE_SIZE.toString())
        }

    internal companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}