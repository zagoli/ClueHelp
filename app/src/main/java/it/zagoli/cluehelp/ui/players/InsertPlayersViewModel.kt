package it.zagoli.cluehelp.ui.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.extensions.MutableLiveList
import it.zagoli.cluehelp.ui.TempStore
import it.zagoli.cluehelp.ui.gameObjectsUtils.NavigationStatus
import timber.log.Timber

class InsertPlayersViewModel : ViewModel() {

    /**
     * backing property for [players]
     */
    private val _players = MutableLiveList<Player>()

    /**
     * list of [Player]
     */
    val players: LiveData<MutableList<Player>>
        get() = _players

    /**
     * backing property for [navigateInsertSuspectsEvent]
     */
    private val _navigateInsertSuspectsEvent = MutableLiveData<NavigationStatus>()

    /**
     * navigation event. We should navigate only if there are at least two players
     */
    val navigateInsertSuspectsEvent: MutableLiveData<NavigationStatus>
        get() = _navigateInsertSuspectsEvent

    /**
     * call when you want to navigate to insert suspect fragment.
     * it will trigger the event only if there are two or more players
     */
    fun onNavigateToInsertSuspects() {
        if(_players.value!!.size > 1) {
            //we save temporarily the players list in the main activity companion object
            //at this point, the value of players is certainly not null
            TempStore.players = players.value!!
            _navigateInsertSuspectsEvent.value = NavigationStatus.OK
        } else {
            _navigateInsertSuspectsEvent.value = NavigationStatus.IMPOSSIBLE
        }
    }

    /**
     * call after the navigation
     */
    fun navigateToInsertSuspectsComplete() {
        _navigateInsertSuspectsEvent.value = NavigationStatus.DONE
    }

    /**
     * add new player in [_players]
     * @param playerName the name of the player
     */
    fun addPlayer(playerName: String) {
        if (playerName.isNotBlank() && playerName.isNotEmpty()){
            val player = Player(playerName)
            _players.addElementToList(player)
            Timber.i("player $playerName added")
        } else {
            Timber.i("empty or blank text, player not added")
        }
    }

    fun removePlayer(player: Player) {
        _players.removeElementFromList(player)
        Timber.i("player ${player.name} deleted")
    }
}