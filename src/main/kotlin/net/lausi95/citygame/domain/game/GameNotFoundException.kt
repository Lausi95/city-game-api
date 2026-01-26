package net.lausi95.citygame.domain.game

import net.lausi95.citygame.domain.NotFoundDomainException

class GameNotFoundException(message: String) : NotFoundDomainException(message)

fun gameNotFound(gameId: GameId): Nothing {
    throw GameNotFoundException("Game not found: $gameId")
}