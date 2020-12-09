package it.zagoli.cluehelp.ui.weapons

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import it.zagoli.cluehelp.domain.GameObject
import it.zagoli.cluehelp.domain.GameObjectType
import it.zagoli.cluehelp.extensions.MutableLiveList
import timber.log.Timber

class InsertWeaponsViewModel : ViewModel() { //init block below!
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
        val defaultWeapons = listOf("Rope", "Lead pipe", "Knife", "Wrench", "Candlestick", "Revolver")
            .map { name ->
                GameObject(name, GameObjectType.WEAPON)
            }
        _weapons.addAll(defaultWeapons)
        Timber.i("default weapons loaded")
    }

    init {
        loadDefaultWeapons()
    }
}