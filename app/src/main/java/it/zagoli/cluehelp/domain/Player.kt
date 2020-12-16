package it.zagoli.cluehelp.domain

data class Player (
    val name: String,
    val notOwnedGameObjects: MutableSet<GameObject> = mutableSetOf()
) {
    override fun toString(): String {
        return name
    }
}
