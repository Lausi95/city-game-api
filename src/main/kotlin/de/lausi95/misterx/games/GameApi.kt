package de.lausi95.misterx.games

import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.stereotype.Component
import java.util.*

data class GameId(val value: String = UUID.randomUUID().toString())
data class GameTitle(val value: String)

data class Game(
  val id: GameId,
  val title: GameTitle
)

data class GameCreatedEvent(
  val id: GameId
)

class GameAlreadyExistsException : RuntimeException()

fun gameAlreadyExistsError(): Nothing = throw GameAlreadyExistsException()

@Component
class GameApi(private val createGameService: CreateGameService) :
  CreateGameService by createGameService

interface CreateGameService {

  @PreAuthorize("hasAnyAuthority('SCOPE_games:create')")
  fun createGame(title: GameTitle): GameId
}
