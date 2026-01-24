package net.lausi95.citygame.domain.game

interface GameRepository {

    fun save(game: Game)

    fun findById(id: GameId): Game?

    fun existsByTitle(title: GameTitle): Boolean
}
