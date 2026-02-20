package net.lausi95.citygame.domain.game

import net.lausi95.citygame.domain.Tenant
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable

interface GameRepository {

    fun save(game: Game, tenant: Tenant)

    fun findById(id: GameId, tenant: Tenant): Game?

    fun existsByTitle(title: GameTitle, tenant: Tenant): Boolean

    fun find(pageable: Pageable, tenant: Tenant): Page<Game>
}
