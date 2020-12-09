package it.zagoli.cluehelp.domain

data class Player (
    val name: String,
    val notOwnedGameObjects: List<GameObject> = mutableListOf()
)
