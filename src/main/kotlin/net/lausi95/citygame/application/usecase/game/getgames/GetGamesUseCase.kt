package net.lausi95.citygame.application.usecase.game.getgames

import net.lausi95.citygame.domain.game.Game
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class GetGamesUseCase {

    operator fun invoke(pageable: Pageable): Page<Game> {
        TODO("implement this")
    }
}