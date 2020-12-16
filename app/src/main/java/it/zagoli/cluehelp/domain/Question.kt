package it.zagoli.cluehelp.domain

import it.zagoli.cluehelp.extensions.GameObjectWrapper

data class Question(
    val gameObjects: MutableList<GameObjectWrapper>,
    val asks: Player,
    val answers: Player,
    private var isValid: Boolean = true
) {
    fun isValid(): Boolean {
        return isValid
    }

    fun invalidate() {
        isValid = false
    }
}
