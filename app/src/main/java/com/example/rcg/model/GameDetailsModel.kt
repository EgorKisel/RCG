package com.example.rcg.model

data class GameDetailsModel(
    val id: Long,
    val title: String,
    val image: String?,
    val description: String,
    val screenshots: List<String>
)
