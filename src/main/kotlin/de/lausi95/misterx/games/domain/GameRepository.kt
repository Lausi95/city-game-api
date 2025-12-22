package de.lausi95.misterx.games.domain

import de.lausi95.misterx.games.Game
import de.lausi95.misterx.games.GameId
import de.lausi95.misterx.games.GameTitle

interface GameRepository {

  fun save(game: Game)

  fun existsById(gameId: GameId): Boolean

  fun existsByTitle(title: GameTitle): Boolean

  fun findAll(): List<Game>
}
