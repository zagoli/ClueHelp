package it.zagoli.cluehelp.ui.gameObjectsUtils

import it.zagoli.cluehelp.domain.GameObject

class GameObjectClickListener(val clickListener: (gameObject: GameObject) -> Unit) {
    fun onClick(gameObject: GameObject) = clickListener(gameObject)
}