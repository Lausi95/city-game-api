package net.lausi95.citygame.bdd

import net.lausi95.citygame.domain.game.Game
import net.lausi95.citygame.domain.game.GameId
import net.lausi95.citygame.domain.game.GameTitle
import java.util.*

fun randomGame(
    id: GameId = GameId(UUID.randomUUID().toString()),
    title: GameTitle = GameTitle("???")
) = Game(id, title)
