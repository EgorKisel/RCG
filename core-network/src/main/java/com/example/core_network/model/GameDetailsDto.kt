package com.example.core_network.model

import com.google.gson.annotations.SerializedName

data class GameDetailsDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val title: String,
    @SerializedName("background_image") val image: String?,
    @SerializedName("description") val description: String
)
