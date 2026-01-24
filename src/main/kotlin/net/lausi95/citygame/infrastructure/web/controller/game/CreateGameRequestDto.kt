package net.lausi95.citygame.infrastructure.web.controller.game

import com.fasterxml.jackson.annotation.JsonProperty
import jakarta.validation.constraints.NotEmpty

data class CreateGameRequestDto(

    @field:JsonProperty("title")
    @field:NotEmpty(message = "endpoint.create-game.validation.title.not-empty")
    var title: String?
)
