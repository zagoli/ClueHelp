package it.zagoli.cluehelp.extensions

import it.zagoli.cluehelp.domain.GameObject

/**
 * Wrapper for [GameObject] in a question. Allows to invalidate an object in a specific question
 */
data class GameObjectWrapper(val gameObject: GameObject, private var isValid: Boolean = true) {

    fun isValid(): Boolean {
        return isValid
    }

    fun invalidate() {
        isValid = false
    }
}