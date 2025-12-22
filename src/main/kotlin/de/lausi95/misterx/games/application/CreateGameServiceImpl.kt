package de.lausi95.misterx.games.application

import de.lausi95.misterx.games.*
import de.lausi95.misterx.games.domain.GameRepository
import org.springframework.context.ApplicationEventPublisher
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional

@Primary
@Component
class CreateGameServiceImpl(
  private val gameRepository: GameRepository,
  private val applicationEventPublisher: ApplicationEventPublisher
) : CreateGameService {

  @Transactional
  override fun createGame(title: GameTitle): GameId {
    if (gameRepository.existsByTitle(title)) {
      gameAlreadyExistsError()
    }

    val gameId = GameId()
    val game = Game(gameId, title)

    gameRepository.save(game)
    applicationEventPublisher.publishEvent(GameCreatedEvent(gameId))

    return gameId
  }
}
