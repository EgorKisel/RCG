package com.example.rcg.viewmodel.details

import com.example.rcg.model.GameDetailsModel
import com.example.rcg.model.base.ListItem
import com.example.rcg.ui.base.model.ScreenshotItem
import com.example.rcg.ui.details.GameDetailsFragmentArgs

sealed class GameDetailsScreenModel {
    abstract val id: Long
    abstract val title: String
    abstract val image: String?

    data class Initial(
        override val id: Long,
        override val title: String,
        override val image: String?,
    ) : GameDetailsScreenModel()

    data class Content(
        override val id: Long,
        override val title: String,
        override val image: String?,
        val description: String,
        val screenshots: List<ListItem>,
    ) : GameDetailsScreenModel()

    data class Error(
        override val id: Long,
        override val title: String,
        override val image: String?,
        val throwable: Throwable,
    ) : GameDetailsScreenModel()

    companion object {
        fun from(args: GameDetailsFragmentArgs) = Initial(
            id = args.gameId,
            title = args.gameTitle,
            image = args.gameImage,
        )

        fun from(model: GameDetailsModel) = Content(
            id = model.id,
            title = model.title,
            image = model.image,
            description = model.description,
            screenshots = model.screenshots.map { ScreenshotItem(it) },
        )

        fun from(initial: Initial, throwable: Throwable) = Error(
            id = initial.id,
            title = initial.title,
            image = initial.image,
            throwable = throwable,
        )
    }
}
