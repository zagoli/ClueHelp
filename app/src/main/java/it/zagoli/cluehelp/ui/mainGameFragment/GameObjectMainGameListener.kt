package it.zagoli.cluehelp.ui.mainGameFragment

import android.view.View
import android.widget.AdapterView
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.Player

class GameObjectMainGameListener(private val viewModel: MainGameViewModel, private val gameObject: GameObject) :
    AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val player = parent.getItemAtPosition(position) as Player
        gameObject.owner = player
        viewModel.newObjectOwnerDiscovered(gameObject)
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}
}