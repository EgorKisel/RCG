package com.example.core_network.api

import com.example.core_network.model.GameDetailsDto
import com.example.core_network.model.ScreenshotsResponse
import com.example.core_network.model.base.PagedResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface RawgApi {

    @GET("/api/games?key=721575b77ad74531b6066e37437c0b07")
    suspend fun games(
        @QueryMap params: Map<String, String>
    ): PagedResponse

    @GET("/api/games/{id}")
    suspend fun gameDetails(@Path("id") id: Long): GameDetailsDto

    @GET("/api/games/{id}/screenshots")
    suspend fun gameScreenshots(@Path("id") id: Long, @Query("page_size") number: Int): ScreenshotsResponse
}