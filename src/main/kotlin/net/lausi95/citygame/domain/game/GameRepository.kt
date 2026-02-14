package net.lausi95.citygame.domain.game

import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GameRepository {

    fun save(game: Game)

    fun findById(id: GameId): Game?

    fun existsByTitle(title: GameTitle): Boolean

    fun find(pageable: Pageable): Page<Game>
}
