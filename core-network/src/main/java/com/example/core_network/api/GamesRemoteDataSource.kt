package com.example.core_network.api

import com.example.core_network.api.params.GamesApiParams
import com.example.core_network.model.GameDto
import java.lang.IllegalStateException
import javax.inject.Inject

class GamesRemoteDataSource @Inject constructor(
    private val api: RawgApi
) {

    private var params: GamesApiParams? = null
    private var page = 1

    //@Synchronized
    suspend fun initialLoading(params: GamesApiParams): List<GameDto> {
        val response = api.games(params.applyPagingParams())
        this.params = params
        return response.results
    }

    //@Synchronized
    suspend fun loadMore(): List<GameDto> {
        val params = this.params ?: throw IllegalStateException("There is no data")
        val response = api.games(params.applyPagingParams(page = page + 1))
        page += 1
        return response.results
    }

    private fun GamesApiParams.applyPagingParams(page: Int = 1): Map<String, String> =
        toMap().toMutableMap()
            .apply {
                put("page", page.toString())
                put("page_size", DEFAULT_PAGE_SIZE.toString())
            }

    private companion object {
        const val DEFAULT_PAGE_SIZE = 20
    }
}