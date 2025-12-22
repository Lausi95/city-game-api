package de.lausi95.misterx.games.adapter.postgresql

import de.lausi95.misterx.games.Game
import de.lausi95.misterx.games.GameId
import de.lausi95.misterx.games.GameTitle
import de.lausi95.misterx.games.domain.GameRepository
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Component

@Entity
@Table(name = "game")
private data class GameEntity(
  @Id
  @Column(name = "id")
  val id: String,

  @Column(name = "title", nullable = false, unique = true)
  val title: String,
) {
  constructor(game: Game) : this(
    game.id.value,
    game.title.value
  )

  fun toGame() = Game(GameId(id), GameTitle(title))
}

private interface GameEntityRepository : JpaRepository<GameEntity, String> {

  fun existsByTitle(title: String): Boolean
}

@Component
private class PostgresqlGameRepository(
  private val gameEntityRepository: GameEntityRepository
) : GameRepository {

  override fun save(game: Game) {
    gameEntityRepository.save(GameEntity(game))
  }

  override fun existsById(gameId: GameId): Boolean {
    return gameEntityRepository.existsById(gameId.value)
  }

  override fun existsByTitle(title: GameTitle): Boolean {
    return gameEntityRepository.existsByTitle(title.value)
  }

  override fun findAll(): List<Game> {
    return gameEntityRepository.findAll().map { it.toGame() }
  }
}