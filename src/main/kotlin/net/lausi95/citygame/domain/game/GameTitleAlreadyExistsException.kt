package net.lausi95.citygame.domain.game

import net.lausi95.citygame.domain.DomainException

class GameTitleAlreadyExistsException(message: String) : DomainException(message)

fun gameTitleAlreadyExists(title: GameTitle): Nothing {
    throw GameTitleAlreadyExistsException("Game title already exist: $title")
}