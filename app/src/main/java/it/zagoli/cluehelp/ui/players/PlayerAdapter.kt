package it.zagoli.cluehelp.ui.players

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.zagoli.cluehelp.databinding.InsertPlayerListElementBinding
import it.zagoli.cluehelp.domain.Player

class PlayerAdapter(private val clickListener: PlayerClickListener): ListAdapter<Player, PlayerViewHolder>(PlayerDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayerViewHolder {
        return PlayerViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: PlayerViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener)
    }
}

class PlayerDiffCallback: DiffUtil.ItemCallback<Player>() {

    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        return oldItem == newItem
    }

}

class PlayerViewHolder(private val binding: InsertPlayerListElementBinding): RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): PlayerViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = InsertPlayerListElementBinding.inflate(layoutInflater, parent, false)
            return PlayerViewHolder(binding)
        }
    }

    fun bind(item: Player, clickListener: PlayerClickListener) {
        binding.player = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }
}

class PlayerClickListener(val clickListener: (player: Player) -> Unit){
    fun onClick(player: Player) = clickListener(player)
}