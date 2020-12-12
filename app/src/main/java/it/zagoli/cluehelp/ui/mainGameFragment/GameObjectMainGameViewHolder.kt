package it.zagoli.cluehelp.ui.mainGameFragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.RecyclerView
import it.zagoli.cluehelp.databinding.MainGameListElementBinding
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.Player

class GameObjectMainGameViewHolder(
    private val binding: MainGameListElementBinding,
    private val viewModel: MainGameViewModel
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        fun from(parent: ViewGroup, viewModel: MainGameViewModel): GameObjectMainGameViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding = MainGameListElementBinding.inflate(layoutInflater, parent, false)
            return GameObjectMainGameViewHolder(binding, viewModel)
        }
    }

    fun bind(
        gameObject: GameObject,
        playerAdapter: ArrayAdapter<Player>
    ) {
        binding.spinnerGameObjectOwner.adapter = playerAdapter
        binding.spinnerGameObjectOwner.onItemSelectedListener =
            GameObjectMainGameListener(viewModel = viewModel, gameObject = gameObject)
        binding.gameObject = gameObject
        binding.executePendingBindings()
    }

}
