package com.example.rcg.repository.model

sealed class CategoryType {
    object MostAnticipated : CategoryType()
    object LatestReleases : CategoryType()
    object Rated : CategoryType()

    data class Genre(val id: Long) : CategoryType()
}
