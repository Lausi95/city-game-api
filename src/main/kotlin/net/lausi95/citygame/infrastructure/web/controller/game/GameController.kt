package net.lausi95.citygame.infrastructure.web.controller.game

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.headers.Header
import io.swagger.v3.oas.annotations.media.Content
import io.swagger.v3.oas.annotations.media.Schema
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import net.lausi95.citygame.application.usecase.creategame.CreateGameRequest
import net.lausi95.citygame.application.usecase.creategame.CreateGameUseCase
import net.lausi95.citygame.application.usecase.getgame.GetGameUseCase
import net.lausi95.citygame.domain.game.GameId
import net.lausi95.citygame.domain.game.GameTitle
import org.springframework.http.ProblemDetail
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.support.ServletUriComponentsBuilder

@RestController
@RequestMapping("/games")
class GameController(
    private val createGameUseCase: CreateGameUseCase,
    private val getGameUseCase: GetGameUseCase,
) {

    @PostMapping
    @Operation(summary = "Creates a new game")
    @ApiResponse(
        responseCode = "201",
        description = "Game was created successfully",
        headers = [Header(name = "location", description = "URI of the new game")],
        content = [],
    )
    @ApiResponse(
        responseCode = "400",
        description = "Input Validation Errors",
        content = [Content(mediaType = "application/json", schema = Schema(ProblemDetail::class))],
    )
    @ApiResponse(
        responseCode = "500",
        description = "Internal Server Error",
        content = [Content(mediaType = "application/json", schema = Schema(ProblemDetail::class))],
    )
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

    @GetMapping("/{gameId}")
    fun getGame(
        @PathVariable gameId: String
    ): GameResource {
        val game = getGameUseCase(GameId(gameId))
        return GameResource(game)
    }
}
