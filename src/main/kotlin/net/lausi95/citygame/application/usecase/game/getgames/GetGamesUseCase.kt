package net.lausi95.citygame.application.usecase.game.getgames

import net.lausi95.citygame.domain.Tenant
import net.lausi95.citygame.domain.game.Game
import net.lausi95.citygame.domain.game.GameRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetGamesUseCase(
    private val gameRepository: GameRepository
) {

    operator fun invoke(pageable: Pageable, tenant: Tenant): Page<Game> {
        return gameRepository.find(pageable, tenant)
    }
}
