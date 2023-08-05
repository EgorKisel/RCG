package com.example.core_network.api

import com.example.core_network.api.params.GamesApiParams
import com.example.core_network.model.GameDto
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.asSharedFlow
import javax.inject.Inject

class GamesRemoteDataSource @Inject constructor(
    private val api: RawgApi
) {

    //private val channel = ConflatedBroadcastChannel<PagingState>()

    private val _channel = MutableSharedFlow<PagingState<List<GameDto>>>()

    val channel: SharedFlow<PagingState<List<GameDto>>> = _channel.asSharedFlow()

    suspend fun initialLoading(params: GamesApiParams) {
        _channel.emit(PagingState.Initial)
        val response = api.games(params.toMap())
        _channel.emit(PagingState.Content(response.results))
    }

    suspend fun loadMore() {

    }

    //fun observe(): Flow<PagingState> = channel.asFlow()

}