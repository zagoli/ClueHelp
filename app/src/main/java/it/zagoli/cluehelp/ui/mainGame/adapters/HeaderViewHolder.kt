package it.zagoli.cluehelp.ui.mainGame.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import it.zagoli.cluehelp.databinding.HeaderBinding

class HeaderViewHolder(private val binding: HeaderBinding): RecyclerView.ViewHolder(binding.root) {
    companion object {
        fun from(parent: ViewGroup): HeaderViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = HeaderBinding.inflate(layoutInflater, parent, false)
            return HeaderViewHolder(binding)
        }
    }

    fun bind(header: DataItem.Header) {
        binding.headerTextResource = header.titleResource
    }
}