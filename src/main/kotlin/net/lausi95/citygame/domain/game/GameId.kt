package net.lausi95.citygame.domain.game

import java.util.*

@JvmInline
value class GameId(val value: String) {
    companion object {
        fun random() = GameId(UUID.randomUUID().toString())
    }

    override fun toString(): String = value
}
