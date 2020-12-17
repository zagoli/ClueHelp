package it.zagoli.cluehelp.domain

data class GameObject(
    val name: String,
    val type: GameObjectType,
    var owner: Player? = null,
    private var _isOwnerCalculated: Boolean = false //doesn't really belong to the model but whatever. It's to color the views
) {
    override fun toString(): String {
        return name
    }
    fun setOwnerCalculated() {
        _isOwnerCalculated = true
    }
    val isOwnerCalculated: Boolean
        get() = _isOwnerCalculated
}
