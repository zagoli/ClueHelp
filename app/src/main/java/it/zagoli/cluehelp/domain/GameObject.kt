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

    override fun hashCode(): Int {
        return name.hashCode().xor(type.hashCode())
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GameObject

        if (name != other.name) return false
        if (type != other.type) return false

        return true
    }
}
