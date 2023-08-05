package com.example.core_network.api.params

data class GamesApiParams(
    val page: Int? = null,
    val pageSize: Int? = null,
    val dates: String? = null,
    val ordering: String? = null
) {

    fun toMap(): Map<String, String> = mutableMapOf<String, String>().apply {
        page?.let { put("page", it.toString()) }
        pageSize?.let { put("page_size", it.toString()) }
        dates?.let { put("dates", it) }
        ordering?.let { put("ordering", it) }
    }
}