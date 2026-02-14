package net.lausi95.citygame.infrastructure.postgresql.game

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

    override fun save(game: Game) {
        val gameEntity = PostgresqlGameEntity(game)
        postgresqlGameEntityRepository.save(gameEntity)
    }

    override fun findById(id: GameId): Game? {
        return postgresqlGameEntityRepository.findById(id.value).orElse(null)?.toGame()
    }

    override fun existsByTitle(title: GameTitle): Boolean {
        return postgresqlGameEntityRepository.existsByTitle(title.value)
    }

    override fun find(pageable: Pageable): Page<Game> {
        TODO("Not yet implemented")
    }
}
