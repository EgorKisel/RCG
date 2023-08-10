package com.example.core_network.api.params

data class GamesApiParams(
    val dates: String? = null,
    val ordering: String? = null
) {

    fun toMap(): Map<String, String> = mutableMapOf<String, String>().apply {
        dates?.let { put("dates", it) }
        ordering?.let { put("ordering", it) }
    }
}