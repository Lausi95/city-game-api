package net.lausi95.citygame.application.usecase.creategame

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.stereotype.Service

private val log = KotlinLogging.logger { }

@Service
class CreateGameUseCase {

    operator fun invoke(request: CreateGameRequest): CreateGameResponse {
        log.info { "game created" }
        TODO("Implement this")
    }
}