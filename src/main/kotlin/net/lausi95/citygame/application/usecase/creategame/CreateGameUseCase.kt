package net.lausi95.citygame.application.usecase.creategame

import io.github.oshai.kotlinlogging.KotlinLogging
import net.lausi95.citygame.domain.game.Game
import net.lausi95.citygame.domain.game.GameId
import net.lausi95.citygame.domain.game.GameRepository
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger { }

@Service
class CreateGameUseCase(
    private val gameRepository: GameRepository,
) {

    operator fun invoke(request: CreateGameRequest): CreateGameResponse {
        require(!gameRepository.existsByTitle(request.title)) {
            "Game with title already exist. Title: '${request.title}'"
        }

        val game = Game(GameId.random(), request.title)
        gameRepository.save(game)

        log.info { "game created" }

        return CreateGameResponse(
            gameId = game.id
        )
    }
}
