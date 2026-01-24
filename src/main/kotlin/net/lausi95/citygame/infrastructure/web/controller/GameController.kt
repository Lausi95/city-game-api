package net.lausi95.citygame.infrastructure.web.controller

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

private val log = KotlinLogging.logger { }

@RestController
@RequestMapping("/games")
class GameController {

    @PostMapping
    fun postGame() {
        log.info { "game created" }
    }
}
