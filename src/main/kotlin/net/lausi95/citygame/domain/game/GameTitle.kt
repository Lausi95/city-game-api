package net.lausi95.citygame.domain.game

@JvmInline
value class GameTitle(val value: String) {

    companion object {}

    override fun toString(): String = value
}
