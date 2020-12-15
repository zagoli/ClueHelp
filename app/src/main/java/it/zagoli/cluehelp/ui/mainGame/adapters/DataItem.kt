package it.zagoli.cluehelp.ui.mainGame.adapters

import it.zagoli.cluehelp.domain.GameObject

sealed class DataItem {

    abstract val id: Long

    data class GameObjectItem(val gameObject: GameObject): DataItem() {
        override val id = gameObject.hashCode().toLong()
    }

    data class Header(val titleResource: Int): DataItem() {
        override val id = titleResource.toLong()
    }

}