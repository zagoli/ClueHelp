package it.zagoli.cluehelp.domain

data class Question(
    val gameObjects: MutableList<Pair<GameObject, Boolean>>,
    val questioner: Player,
    val questioned: Player,
    var isValid: Boolean = true
)
