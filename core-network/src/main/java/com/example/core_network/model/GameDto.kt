package com.example.core_network.model

import com.google.gson.annotations.SerializedName

data class GameDto(
    @SerializedName("id") val id: Long,
    @SerializedName("name") val title: String,
    @SerializedName("background_image") val image: String?,
    @SerializedName("released") val released: String,
    @SerializedName("added") val added: Long,
    @SerializedName("rating") val rating: Float
)