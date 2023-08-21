package com.example.rcg.interactor.details

import com.example.rcg.viewmodel.details.GameDetailsScreenModel

interface GameDetailsInteractor {

    suspend fun data(): GameDetailsScreenModel
}