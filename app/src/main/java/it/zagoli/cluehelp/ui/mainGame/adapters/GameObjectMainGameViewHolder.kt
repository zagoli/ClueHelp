package it.zagoli.cluehelp.ui.mainGame.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.core.view.get
import androidx.recyclerview.widget.RecyclerView
import it.zagoli.cluehelp.databinding.MainGameListElementBinding
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.ui.mainGame.MainGameViewModel

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
        playerAdapter: ArrayAdapter<Player>,
        selectedPlayerPosition: Int
    ) {
        playerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        // we enable the view in case this was an already used and disabled view
        binding.spinnerGameObjectOwner.isEnabled = true
        binding.spinnerGameObjectOwner.adapter = playerAdapter
        binding.spinnerGameObjectOwner.onItemSelectedListener = GameObjectMainGameListener(viewModel = viewModel, gameObject = gameObject)
        binding.spinnerGameObjectOwner.setSelection(selectedPlayerPosition)
        binding.gameObject = gameObject
        if (selectedPlayerPosition != 0) {
            // if the owner of the object is not the placeholder player, we disable the view.
            // this means we can set the gameObject owner only once
            binding.spinnerGameObjectOwner.isEnabled = false
        }
        binding.executePendingBindings()
    }

}
