package it.zagoli.cluehelp.ui.players

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.zagoli.cluehelp.domain.Player
import it.zagoli.cluehelp.extensions.MutableLiveList
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
     * backing property for [navigateInsertSuspectEvent]
     */
    private val _navigateInsertSuspectEvent = MutableLiveData<NavigationStatus>()

    /**
     * navigation event. We should navigate only if there are at least two players
     */
    val navigateInsertSuspectEvent: MutableLiveData<NavigationStatus>
        get() = _navigateInsertSuspectEvent

    /**
     * call when you want to navigate to insert suspect fragment.
     * it will trigger the event only if there are two or more players
     */
    fun onNavigateToInsertSuspects() {
        if(_players.value!!.size > 1) {
            _navigateInsertSuspectEvent.value = NavigationStatus.OK
        } else {
            _navigateInsertSuspectEvent.value = NavigationStatus.IMPOSSIBLE
        }
    }

    /**
     * call after the navigation
     */
    fun navigateToInsertSuspectsComplete() {
        _navigateInsertSuspectEvent.value = NavigationStatus.DONE
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

    enum class NavigationStatus {
        OK, //trigger navigation
        DONE, //navigation completed
        IMPOSSIBLE //can't navigate, there are less than two players
    }
}