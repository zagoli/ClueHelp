package it.zagoli.cluehelp.ui.weapons

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

class InsertWeaponsViewModel(application: Application) : AndroidViewModel(application) { //init block below!
    /**
     * backing property for [weapons]
     */
    private val _weapons = MutableLiveList<GameObject>()

    /**
     * list of [GameObject] of weapon type
     */
    val weapons: LiveData<MutableList<GameObject>>
        get() = _weapons

    /**
     * backing property for [navigateInsertRoomsEvent]
     */
    private val _navigateInsertRoomsEvent = MutableLiveData<NavigationStatus>()

    /**
     * navigation event. We should navigate only if there are at least six weapons
     */
    val navigateInsertRoomsEvent: MutableLiveData<NavigationStatus>
        get() = _navigateInsertRoomsEvent

    /**
     * call when you want to navigate to insert rooms fragment.
     * it will trigger the event only if there are six or more suspects
     */
    fun onNavigateToInsertRooms() {
        if(_weapons.value!!.size > 5) {
            //we remove weapons that we may have added previously (we are here by back navigation)
            TempStore.gameObjects.removeIf { g -> g.type == GameObjectType.WEAPON }
            //we save temporarily the suspects list in the main activity companion object
            //at this point, the value of suspects is certainly not null
            TempStore.gameObjects.addAll(weapons.value!!)
            _navigateInsertRoomsEvent.value = NavigationStatus.OK
        } else {
            _navigateInsertRoomsEvent.value = NavigationStatus.IMPOSSIBLE
        }
    }

    /**
     * call after the navigation
     */
    fun navigateToInsertRoomsComplete() {
        _navigateInsertRoomsEvent.value = NavigationStatus.DONE
    }

    /**
     * add new weapon in [_weapons]
     * @param weaponName the name of the weapon
     */
    fun addWeapon(weaponName: String) {
        if (weaponName.isNotBlank() && weaponName.isNotEmpty()) {
            val weapon = GameObject(weaponName, GameObjectType.WEAPON)
            _weapons.addElementToList(weapon)
            Timber.i("weapon $weaponName added")
        } else {
            Timber.i("empty or blank text, weapon not added")
        }
    }

    /**
     * remove weapon from [_weapons]
     * @param weapon the [GameObject] that represents the weapon you want to remove
     */
    fun removeWeapon(weapon: GameObject) {
        _weapons.removeElementFromList(weapon)
        Timber.i("weapon ${weapon.name} deleted")
    }

    /**
     * loads the default weapons in [_weapons]
     */
    private fun loadDefaultWeapons() {
        val defaultWeapons = getWeaponsNamesFromResources().map { name ->
                GameObject(name, GameObjectType.WEAPON)
            }
        _weapons.addAll(defaultWeapons)
        Timber.i("default weapons loaded")
    }

    private fun getWeaponsNamesFromResources(): List<String> {
        val app = getApplication<ClueHelpApplication>()
        return listOf(
            app.getString(R.string.Rope),
            app.getString(R.string.Lead_pipe),
            app.getString(R.string.Knife),
            app.getString(R.string.Wrench),
            app.getString(R.string.Candlestick),
            app.getString(R.string.Revolver)
        )
    }

    init {
        loadDefaultWeapons()
    }
}