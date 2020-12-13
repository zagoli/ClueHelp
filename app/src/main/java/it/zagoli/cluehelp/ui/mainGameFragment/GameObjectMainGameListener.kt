package it.zagoli.cluehelp.ui.mainGameFragment

import android.view.View
import android.widget.AdapterView
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.Player
import timber.log.Timber

class GameObjectMainGameListener(
    private val viewModel: MainGameViewModel,
    private val gameObject: GameObject
) :
    AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
        val player = parent.getItemAtPosition(position) as Player
        // if the selected player is not the placeholder player
        // problem: recyclerview recreates the spinner every time it exits and the re-enter the screen.
        // so, every time it will assign a new Listener to it. This will trigger a "onItemSelectedEvent".
        // we want to avoid this, so when this method is called we'll check if the gameObject passed is already
        // associated with an owner. If yes, we''l ignore the event
        if (player.name != "" && gameObject.owner == null) {
            gameObject.owner = player
            viewModel.newObjectOwnerDiscovered(gameObject)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {}
}