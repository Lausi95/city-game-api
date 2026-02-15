package net.lausi95.citygame.application.usecase.game.creategame

import net.lausi95.citygame.domain.Tenant
import net.lausi95.citygame.domain.game.GameTitle

data class CreateGameCommand(
    val tenant: Tenant,
    val title: GameTitle
)