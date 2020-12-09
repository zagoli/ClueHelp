package it.zagoli.cluehelp.ui.gameObjectsUtils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.zagoli.cluehelp.databinding.InsertGameObjectListElementBinding
import it.zagoli.cluehelp.domain.GameObject

class GameObjectAdapter(private val clickListener: GameObjectClickListener): ListAdapter<GameObject, GameObjectViewHolder> (GameObjectDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameObjectViewHolder {
        return GameObjectViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: GameObjectViewHolder, position: Int) {
        holder.bind(getItem(position), clickListener)
    }

}

class GameObjectViewHolder(private val binding: InsertGameObjectListElementBinding): RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): GameObjectViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = InsertGameObjectListElementBinding.inflate(layoutInflater, parent, false)
            return GameObjectViewHolder(binding)
        }
    }

    fun bind(item: GameObject, clickListener: GameObjectClickListener) {
        binding.gameObject = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }

}