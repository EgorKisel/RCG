package com.example.core_network.api

import com.example.core_network.model.base.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RawgApi {

    @GET("/api/games?key=721575b77ad74531b6066e37437c0b07")
    suspend fun games(
        @QueryMap params: Map<String, String>
    ): PagedResponse
}