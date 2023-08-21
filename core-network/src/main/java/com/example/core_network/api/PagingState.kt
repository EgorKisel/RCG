package com.example.core_network.api

sealed class PagingState<out T> {

    object Initial : PagingState<Nothing>()
    data class Content<T>(val data: T) : PagingState<T>()
    data class Paging<T>(val availableContent: T) : PagingState<T>()
    data class Persist<T>(val data: T) : PagingState<T>()
    data class Error(val throwable: Throwable) : PagingState<Nothing>()

}
