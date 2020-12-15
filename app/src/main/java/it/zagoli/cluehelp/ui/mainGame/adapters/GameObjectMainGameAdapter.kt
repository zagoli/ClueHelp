package it.zagoli.cluehelp.ui.mainGame.adapters

import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import it.zagoli.cluehelp.R
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.extensions.rooms
import it.zagoli.cluehelp.extensions.suspects
import it.zagoli.cluehelp.extensions.weapons
import it.zagoli.cluehelp.ui.mainGame.MainGameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


private const val ITEM_VIEW_TYPE_HEADER = 0
private const val ITEM_VIEW_TYPE_ITEM = 1

class GameObjectMainGameAdapter(
    private val viewModel: MainGameViewModel,
    private val playerAdapter: ArrayAdapter<Player>,
    private val players: Array<Player>
) : ListAdapter<DataItem, RecyclerView.ViewHolder>(DataItemDiffCallback()) {

    private val adapterScope = CoroutineScope(Dispatchers.Default)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        return when (viewType) {
            ITEM_VIEW_TYPE_HEADER -> HeaderViewHolder.from(parent)
            ITEM_VIEW_TYPE_ITEM -> GameObjectMainGameViewHolder.from(parent, viewModel)
            else -> throw ClassCastException("Unknown viewType $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (getItem(position)) {
            is DataItem.Header -> ITEM_VIEW_TYPE_HEADER
            is DataItem.GameObjectItem -> ITEM_VIEW_TYPE_ITEM
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is GameObjectMainGameViewHolder -> {
                val gameObject = (getItem(position) as DataItem.GameObjectItem).gameObject
                val selectedPlayerPosition =
                    if (gameObject.owner == null) {
                        0
                    } else {
                        players.indexOf(gameObject.owner)
                    }
                holder.bind(gameObject, playerAdapter, selectedPlayerPosition)
            }
            is HeaderViewHolder -> {
                val headerItem = getItem(position) as DataItem.Header
                holder.bind(headerItem)
            }
        }
    }

    fun addHeadersAndSubmitList(list: List<GameObject>) {
        adapterScope.launch {
            val suspectsHeader = DataItem.Header(R.string.suspects_header)
            val weaponsHeader = DataItem.Header(R.string.weapons_header)
            val roomsHeader = DataItem.Header(R.string.rooms_header)
            val items = listOf(suspectsHeader) + list.suspects.map { DataItem.GameObjectItem(it) } + listOf(weaponsHeader) + list.weapons.map {
                DataItem.GameObjectItem(
                    it
                )
            } + listOf(roomsHeader) + list.rooms.map { DataItem.GameObjectItem(it) }
            withContext(Dispatchers.Main) {
                submitList(items)
            }
        }
    }
}