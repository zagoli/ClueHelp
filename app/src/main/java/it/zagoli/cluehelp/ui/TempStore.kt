package it.zagoli.cluehelp.ui

import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.Player

abstract class TempStore {
    companion object {
        /**
         * used to store the players list temporarily.
         * I prefer this method to passing this in the bundle.
         * Assigned one time only
         */
        lateinit var players: List<Player>
        /**
         * used to store the players list temporarily.
         * it's a [MutableList] because we need to incrementally add values to it (three times)
         */
        var gameObjects: MutableList<GameObject> = mutableListOf()
    }
}