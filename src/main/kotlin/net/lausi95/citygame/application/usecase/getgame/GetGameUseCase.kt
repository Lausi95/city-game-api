package net.lausi95.citygame.application.usecase.getgame

import net.lausi95.citygame.domain.game.Game
import net.lausi95.citygame.domain.game.GameId
import net.lausi95.citygame.domain.game.GameRepository
import net.lausi95.citygame.domain.game.gameNotFound
import org.springframework.stereotype.Service

@Service
class GetGameUseCase(
    private val gameRepository: GameRepository
) {

    operator fun invoke(gameId: GameId): Game {
        return gameRepository.findById(gameId) ?: gameNotFound(gameId)
    }
}
