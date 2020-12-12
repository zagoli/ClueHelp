package it.zagoli.cluehelp.ui.mainGameFragment

import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.ListAdapter
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.ui.gameObjectsUtils.GameObjectDiffCallback

class GameObjectMainGameAdapter(
    private val viewModel: MainGameViewModel,
    private val playerAdapter: ArrayAdapter<Player>
) : ListAdapter<GameObject, GameObjectMainGameViewHolder>(GameObjectDiffCallback()) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameObjectMainGameViewHolder {
        return GameObjectMainGameViewHolder.from(parent, viewModel)
    }

    override fun onBindViewHolder(holder: GameObjectMainGameViewHolder, position: Int) {
        holder.bind(getItem(position), playerAdapter)
    }
}