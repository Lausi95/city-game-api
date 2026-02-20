package net.lausi95.citygame.infrastructure.postgresql.game

import net.lausi95.citygame.domain.Tenant
import net.lausi95.citygame.domain.game.Game
import net.lausi95.citygame.domain.game.GameId
import net.lausi95.citygame.domain.game.GameRepository
import net.lausi95.citygame.domain.game.GameTitle
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Component

@Component
class PostgresqlGameRepository(
    private val postgresqlGameEntityRepository: PostgresqlGameEntityRepository
) : GameRepository {

    override fun save(game: Game, tenant: Tenant) {
        val gameEntity = PostgresqlGameEntity(game, tenant)
        postgresqlGameEntityRepository.save(gameEntity)
    }

    override fun findById(id: GameId, tenant: Tenant): Game? {
        return postgresqlGameEntityRepository.findByIdAndTenant(id.value, tenant.value)?.toGame()
    }

    override fun existsByTitle(title: GameTitle, tenant: Tenant): Boolean {
        return postgresqlGameEntityRepository.existsByTitleAndTenant(title.value, tenant.value)
    }

    override fun find(pageable: Pageable, tenant: Tenant): Page<Game> {
        return postgresqlGameEntityRepository.findAllByTenant(pageable, tenant.value).map { it.toGame() }
    }
}
