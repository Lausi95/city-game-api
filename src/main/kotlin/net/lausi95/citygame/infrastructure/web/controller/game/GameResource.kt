package net.lausi95.citygame.infrastructure.web.controller.game

import com.fasterxml.jackson.annotation.JsonProperty
import net.lausi95.citygame.domain.game.Game

data class GameResource(
    @field:JsonProperty("id")
    val id: String,

    @field:JsonProperty("title")
    val title: String,

    @field:JsonProperty("links")
    val links: Map<String, String>
) {
    constructor(game: Game) : this(
        id = game.id.value,
        title = game.title.value,
        links = mapOf(
            "self" to "/games/${game.id.value}"
        )
    )
}
