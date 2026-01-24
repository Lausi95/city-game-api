package net.lausi95.citygame.application.usecase.creategame

import net.lausi95.citygame.domain.game.GameTitle

data class CreateGameRequest(
    val title: GameTitle
)