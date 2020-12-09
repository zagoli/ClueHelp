package it.zagoli.cluehelp.ui.suspects

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.zagoli.cluehelp.databinding.InsertSuspectListElementBinding
import it.zagoli.cluehelp.domain.GameObject

class SuspectAdapter(private val clickListener: SuspectClickListener): ListAdapter<GameObject, SuspectViewHolder>(SuspectDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuspectViewHolder {
        return SuspectViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: SuspectViewHolder, position: Int) {
        holder.bind(getItem(position),clickListener)
    }
}

class SuspectDiffCallback: DiffUtil.ItemCallback<GameObject>() {

    override fun areItemsTheSame(oldItem: GameObject, newItem: GameObject): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: GameObject, newItem: GameObject): Boolean {
        return oldItem == newItem
    }

}

class SuspectViewHolder(private val binding: InsertSuspectListElementBinding): RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup): SuspectViewHolder{
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = InsertSuspectListElementBinding.inflate(layoutInflater, parent, false)
            return SuspectViewHolder(binding)
        }
    }

    fun bind(item: GameObject, clickListener: SuspectClickListener) {
        binding.suspect = item
        binding.clickListener = clickListener
        binding.executePendingBindings()
    }
}

class SuspectClickListener(val clickListener: (suspect: GameObject) -> Unit){
    fun onClick(suspect: GameObject) = clickListener(suspect)
}