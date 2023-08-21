package com.example.rcg.repository.model

import com.example.core_network.api.params.GamesApiParams

sealed class CategoryType(
    val internalId: Int,
    val filters: GamesApiParams
) {
    object MostAnticipated : CategoryType(
        0, GamesApiParams(
            dates = "2023-08-03,2024-08-03",
            ordering = "-added"
        )
    )
    object LatestReleases : CategoryType(
        1, GamesApiParams(
            dates = "2023-07-01,2023-08-07",
            ordering = "-released"
        )
    )
    object Rated : CategoryType(
        2, GamesApiParams(
            dates = "2023-01-01,2023-08-04",
            ordering = "-rated"
        )
    )
}
