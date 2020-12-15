package it.zagoli.cluehelp.ui.suspects

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import it.zagoli.cluehelp.ClueHelpApplication
import it.zagoli.cluehelp.R
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.GameObjectType
import it.zagoli.cluehelp.extensions.MutableLiveList
import it.zagoli.cluehelp.ui.TempStore
import it.zagoli.cluehelp.ui.NavigationStatus
import timber.log.Timber

class InsertSuspectsViewModel(application: Application) : AndroidViewModel(application) { //init block below!
    /**
     * backing property for [suspects]
     */
    private val _suspects = MutableLiveList<GameObject>()

    /**
     * list of [GameObject] of suspect type
     */
    val suspects: LiveData<MutableList<GameObject>>
        get() = _suspects

    /**
     * backing property for [navigateInsertWeaponsEvent]
     */
    private val _navigateInsertWeaponsEvent = MutableLiveData<NavigationStatus>()

    /**
     * navigation event. We should navigate only if there are at least six suspects
     */
    val navigateInsertWeaponsEvent: MutableLiveData<NavigationStatus>
        get() = _navigateInsertWeaponsEvent

    /**
     * call when you want to navigate to insert weapons fragment.
     * it will trigger the event only if there are six or more suspects
     */
    fun onNavigateToInsertWeapons() {
        if (_suspects.value!!.size > 5) {
            //we remove suspects that we may have added previously (we are here by back navigation)
            TempStore.gameObjects.removeIf { g -> g.type == GameObjectType.SUSPECT }
            //we save temporarily the suspects list in the main activity companion object
            //at this point, the value of suspects is certainly not null
            TempStore.gameObjects.addAll(suspects.value!!)
            _navigateInsertWeaponsEvent.value = NavigationStatus.OK
        } else {
            _navigateInsertWeaponsEvent.value = NavigationStatus.IMPOSSIBLE
        }
    }

    /**
     * call after the navigation
     */
    fun navigateToInsertWeaponsComplete() {
        _navigateInsertWeaponsEvent.value = NavigationStatus.DONE
    }

    /**
     * add new suspect in [_suspects]
     * @param suspectName the name of the suspect
     */
    fun addSuspect(suspectName: String) {
        if (suspectName.isNotBlank() && suspectName.isNotEmpty()) {
            val suspect = GameObject(suspectName, GameObjectType.SUSPECT)
            _suspects.addElementToList(suspect)
            Timber.i("suspect $suspectName added")
        } else {
            Timber.i("empty or blank text, suspect not added")
        }
    }

    /**
     * remove suspect from [_suspects]
     * @param suspect the [GameObject] that represents the suspect you want to remove
     */
    fun removeSuspect(suspect: GameObject) {
        _suspects.removeElementFromList(suspect)
        Timber.i("suspect ${suspect.name} deleted")
    }

    /**
     * loads the default suspects in [_suspects]
     */
    private fun loadDefaultSuspects() {
        val defaultSuspects = getSuspectsNamesFromResources().map { name ->
            GameObject(name, GameObjectType.SUSPECT)
        }
        _suspects.addAll(defaultSuspects)
        Timber.i("default suspects loaded")
    }

    private fun getSuspectsNamesFromResources(): List<String> {
        val app = getApplication<ClueHelpApplication>()
        return listOf(
            app.getString(R.string.Plum),
            app.getString(R.string.White),
            app.getString(R.string.Scarlet),
            app.getString(R.string.Green),
            app.getString(R.string.Mustard),
            app.getString(R.string.Peacock)
        )
    }

    init {
        loadDefaultSuspects()
    }
}