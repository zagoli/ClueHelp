package it.zagoli.cluehelp.domain

data class Question(
    val gameObjects: List<Pair<GameObject, Boolean>> = mutableListOf(),
    val questioner: Player,
    val questioned: Player,
    var isValid: Boolean = true
)
