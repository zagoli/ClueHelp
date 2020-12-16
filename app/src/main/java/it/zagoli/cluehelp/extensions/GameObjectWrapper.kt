package it.zagoli.cluehelp.extensions

import it.zagoli.cluehelp.domain.GameObject

data class GameObjectWrapper(val gameObject: GameObject, private var isValid: Boolean = true) {

    fun isValid(): Boolean {
        return isValid
    }

    fun invalidate() {
        isValid = false
    }
}