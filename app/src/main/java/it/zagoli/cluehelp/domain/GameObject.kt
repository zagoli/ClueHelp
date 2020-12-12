package it.zagoli.cluehelp.domain

data class GameObject(
    val name: String,
    val type: GameObjectType,
    var owner: Player? = null
) {

    override fun toString(): String {
        return name
    }
}
