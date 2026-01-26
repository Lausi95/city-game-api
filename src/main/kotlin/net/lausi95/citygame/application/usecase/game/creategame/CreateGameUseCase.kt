package net.lausi95.citygame.application.usecase.game.creategame

import io.github.oshai.kotlinlogging.KotlinLogging
import net.lausi95.citygame.domain.game.Game
import net.lausi95.citygame.domain.game.GameId
import net.lausi95.citygame.domain.game.GameRepository
import net.lausi95.citygame.domain.game.gameTitleAlreadyExists
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

private val log = KotlinLogging.logger { }

@Service
class CreateGameUseCase(
    private val gameRepository: GameRepository,
) {

    @Transactional
    operator fun invoke(request: CreateGameRequest): CreateGameResponse {
        if (gameRepository.existsByTitle(request.title)) {
            gameTitleAlreadyExists(request.title)
        }

        val game = Game(GameId.random(), request.title)
        gameRepository.save(game)

        log.info { "game created" }

        return CreateGameResponse(
            gameId = game.id
        )
    }
}
