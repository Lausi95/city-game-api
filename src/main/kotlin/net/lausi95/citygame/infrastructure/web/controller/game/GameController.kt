package net.lausi95.citygame.infrastructure.web.controller.game

import jakarta.validation.Valid
import net.lausi95.citygame.application.usecase.creategame.CreateGameRequest
import net.lausi95.citygame.application.usecase.creategame.CreateGameUseCase
import net.lausi95.citygame.domain.game.GameTitle
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/games")
class GameController(
    private val createGameUseCase: CreateGameUseCase
) {

    @PostMapping
    fun postGame(
        @RequestBody @Valid requestDto: CreateGameRequestDto
    ): ResponseEntity<Unit> {
        val response = createGameUseCase(
            CreateGameRequest(
                title = GameTitle(requestDto.title!!)
            )
        )

        val uri = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path("/games/{gameId}")
            .build(response.gameId.value)

        return ResponseEntity.created(uri).build()
    }
}
