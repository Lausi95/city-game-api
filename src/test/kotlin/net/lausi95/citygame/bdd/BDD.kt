package net.lausi95.citygame.bdd

import net.lausi95.citygame.domain.Tenant
import net.lausi95.citygame.domain.game.Game
import net.lausi95.citygame.domain.game.GameId
import net.lausi95.citygame.domain.game.GameTitle
import java.util.*

fun Tenant.Companion.random() = Tenant(UUID.randomUUID().toString())

fun GameTitle.Companion.random() = GameTitle(UUID.randomUUID().toString())

fun randomGame(
    id: GameId = GameId(UUID.randomUUID().toString()),
    title: GameTitle = GameTitle("???")
) = Game(id, title)
