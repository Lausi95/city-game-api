package net.lausi95.citygame.domain

data class Tenant(val value: String) {
    companion object {
        val DEFAULT = Tenant("default")
    }
}
