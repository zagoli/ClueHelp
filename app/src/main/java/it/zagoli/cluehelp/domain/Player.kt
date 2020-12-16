package it.zagoli.cluehelp.domain

data class Player (
    val name: String,
    val notOwnedGameObjects: MutableSet<GameObject> = mutableSetOf()
) {
    override fun toString(): String {
        return name
    }

    // I have to define equals and hashCode to avoid infinite calls to hashcode. It was very hard to find out
    override fun hashCode(): Int {
        return name.hashCode()
    }

    //auto generated
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Player

        if (name != other.name) return false

        return true
    }
}
