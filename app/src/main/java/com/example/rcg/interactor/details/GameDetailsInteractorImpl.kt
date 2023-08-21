package com.example.rcg.interactor.details

import com.example.rcg.repository.details.GameDetailsRepository
import com.example.rcg.viewmodel.details.GameDetailsScreenModel
import java.lang.Exception
import javax.inject.Inject

class GameDetailsInteractorImpl @Inject constructor(
    private val initialModel: GameDetailsScreenModel.Initial,
    private val repository: GameDetailsRepository
) : GameDetailsInteractor {

    override suspend fun data(): GameDetailsScreenModel =
        try {
            val model = repository.data(initialModel.id)
            GameDetailsScreenModel.from(model)
        } catch (e: Exception) {
            GameDetailsScreenModel.from(initialModel, e)
        }
}