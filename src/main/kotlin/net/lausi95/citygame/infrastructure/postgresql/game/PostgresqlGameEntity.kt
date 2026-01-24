package net.lausi95.citygame.infrastructure.postgresql.game

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import net.lausi95.citygame.domain.game.Game
import net.lausi95.citygame.domain.game.GameId
import net.lausi95.citygame.domain.game.GameTitle

@Entity
@Table(name = "game")
class PostgresqlGameEntity() {

    @Id
    @Column(name = "id")
    var id: String? = null

    @Column(name = "title")
    var title: String? = null

    constructor(game: Game) : this() {
        this.id = game.id.value
        this.title = game.title.value
    }

    fun toGame() = Game(
        _id = GameId(requireNotNull(id)),
        _title = GameTitle(requireNotNull(title))
    )
}
