package net.lausi95.citygame.application.usecase.game.getgame

import net.lausi95.citygame.domain.Tenant
import net.lausi95.citygame.domain.game.Game
import net.lausi95.citygame.domain.game.GameId
import net.lausi95.citygame.domain.game.GameRepository
import net.lausi95.citygame.domain.game.gameNotFound
import org.springframework.stereotype.Service

@Service
class GetGameUseCase(
    private val gameRepository: GameRepository
) {

    operator fun invoke(gameId: GameId, tenant: Tenant): Game {
        return gameRepository.findById(gameId, tenant) ?: gameNotFound(gameId)
    }
}
