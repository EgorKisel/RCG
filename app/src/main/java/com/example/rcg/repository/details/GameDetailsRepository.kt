package com.example.rcg.repository.details

import com.example.rcg.model.GameDetailsModel

interface GameDetailsRepository {

    suspend fun data(id: Long): GameDetailsModel
}