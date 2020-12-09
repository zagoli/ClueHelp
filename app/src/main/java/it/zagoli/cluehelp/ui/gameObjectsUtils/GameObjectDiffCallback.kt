package it.zagoli.cluehelp.ui.gameObjectsUtils

import androidx.recyclerview.widget.DiffUtil
import it.zagoli.cluehelp.domain.GameObject

class GameObjectDiffCallback: DiffUtil.ItemCallback<GameObject>() {

    override fun areItemsTheSame(oldItem: GameObject, newItem: GameObject): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: GameObject, newItem: GameObject): Boolean {
        return oldItem == newItem
    }

}